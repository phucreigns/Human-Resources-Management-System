package com.phuc.auth.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuc.auth.dto.auth0.Auth0TokenResponse;
import com.phuc.auth.dto.auth0.Auth0UserInfo;
import com.phuc.auth.dto.request.RefreshTokenRequest;
import com.phuc.auth.dto.request.UserUpdateRequest;
import com.phuc.auth.dto.response.TokenResponse;
import com.phuc.auth.dto.response.UserResponse;
import com.phuc.auth.entity.User;
import com.phuc.auth.exception.AppException;
import com.phuc.auth.exception.ErrorCode;
import com.phuc.auth.httpclient.Auth0Client;
import com.phuc.auth.httpclient.NotificationClient;
import com.phuc.auth.mapper.UserMapper;
import com.phuc.auth.repository.UserRepository;
import com.phuc.auth.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    Auth0Client auth0Client;
    NotificationClient notificationClient;
    
    @PersistenceContext
    @NonFinal
    EntityManager entityManager;

    @Value("${auth0.client-id}")
    @NonFinal
    String clientId;

    @Value("${auth0.client-secret}")
    @NonFinal
    String clientSecret;

    @Value("${auth0.redirect-uri}")
    @NonFinal
    String redirectUri;

    @Value("${auth0.audience}")
    @NonFinal
    String audience;

    @Value("${auth0.domain}")
    @NonFinal
    String auth0Domain;

    @Value("${app.redirect-url}")
    @NonFinal
    String redirectUrl;


    @Transactional
    @Override
    public ResponseEntity<TokenResponse> login(String code) {
        if (code == null || code.isEmpty()) {
            log.error("Authorization code is required");
            throw new AppException(ErrorCode.CODE_IS_EMPTY);
        }
        try {
            String formData = String.format(
                    "grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&audience=%s&scope=openid%%20profile%%20email%%20offline_access",
                    clientId, clientSecret, code, redirectUri, audience);
            Auth0TokenResponse tokenResponse = auth0Client.exchangeCodeForToken(formData);
            if (tokenResponse.getAccessToken() == null) {
                log.error("Failed to get access token from Auth0");
                throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
            }

            Auth0UserInfo userInfo = auth0Client.getUserInfo("Bearer " + tokenResponse.getAccessToken());
            
            // Try to find user by auth0Id first (most reliable), then by email
            // If not found, create new user. Handle duplicate key exceptions gracefully.
            String auth0Id = userInfo.getSub();
            String email = userInfo.getEmail();
            
            // First, try to find existing user
            User user = userRepository.findByAuth0Id(auth0Id)
                    .orElseGet(() -> {
                        if (email != null && !email.isEmpty()) {
                            return userRepository.findByEmail(email).orElse(null);
                        }
                        return null;
                    });
            
            // If user not found, try to create new user with retry logic
            if (user == null) {
                user = createUserWithRetry(userInfo, auth0Id, email);
            }
            
            // Update user info from Auth0 if user exists but auth0Id is missing or different
            boolean needsUpdate = false;
            if (user.getAuth0Id() == null || !user.getAuth0Id().equals(auth0Id)) {
                user.setAuth0Id(auth0Id);
                needsUpdate = true;
            }
            
            // Update email if it's missing or different
            if ((user.getEmail() == null || user.getEmail().isEmpty()) && email != null && !email.isEmpty()) {
                user.setEmail(email);
                needsUpdate = true;
            }
            
            // Save once if any update is needed
            if (needsUpdate) {
                userRepository.save(user);
                entityManager.flush();
                log.info("Updated user info: auth0Id={}, email={}", auth0Id, email);
            }

            TokenResponse response = TokenResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .expiresIn(tokenResponse.getExpiresIn() != null ? tokenResponse.getExpiresIn().toString() : null)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage(), e);
            String errorMessage = e.getMessage() != null ? e.getMessage() : "";
            if (errorMessage.contains("invalid_grant") || errorMessage.contains("expired") || errorMessage.contains("invalid")) {
                throw new AppException(ErrorCode.CODE_EXPIRED_OR_INVALID);
            }
            throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
        }
    }

    @Transactional
    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        if (request.getRefreshToken() == null || request.getRefreshToken().isEmpty()) {
            log.error("Refresh token is required");
            throw new AppException(ErrorCode.REFRESH_TOKEN_IS_REQUIRED);
        }

        try {
            String formData = String.format(
                    "grant_type=refresh_token&client_id=%s&client_secret=%s&refresh_token=%s&audience=%s&scope=openid%%20profile%%20email%%20offline_access",
                    clientId, clientSecret, request.getRefreshToken(), audience);

            Auth0TokenResponse tokenResponse = auth0Client.refreshToken(formData);
            if (tokenResponse.getAccessToken() == null) {
                log.error("Failed to refresh token");
                throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
            }

            return TokenResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .refreshToken(tokenResponse.getRefreshToken() != null
                            && !tokenResponse.getRefreshToken().isEmpty()
                            ? tokenResponse.getRefreshToken()
                            : request.getRefreshToken())
                    .expiresIn(tokenResponse.getExpiresIn().toString())
                    .build();
        } catch (Exception e) {
            log.error("Refresh token error: {}", e.getMessage(), e);
            throw new AppException(e.getMessage().contains("invalid_grant")
                    ? ErrorCode.REFRESH_TOKEN_IS_INVALID
                    : ErrorCode.EXTERNAL_SERVICE_ERROR);
        }
    }

    @Transactional
    @Override
    public void resetPassword() {
        String email = getCurrentEmail();
        if (email == null || email.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_IS_EMPTY);
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format("https://%s/dbconnections/change_password", auth0Domain);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("client_id", clientId);
            body.put("email", email);
            body.put("connection", "Username-Password-Authentication");

            restTemplate.postForEntity(url,
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(body), headers), String.class);
        } catch (Exception e) {
            log.error("Reset password error: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Void> logout() {
        HttpHeaders headers = new HttpHeaders();
        String domain = auth0Domain.replaceAll("/+$", "");
        String logoutUrl = String.format("https://%s/v2/logout?client_id=%s&returnTo=%s", domain,
                clientId, redirectUrl);
        headers.add("Location", logoutUrl);
        headers.add("Set-Cookie", "access_token=; Path=/; HttpOnly; Max-Age=0; SameSite=Lax");
        headers.add("Set-Cookie", "refresh_token=; Path=/; HttpOnly; Max-Age=0; SameSite=Lax");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    /**
     * Create user with retry logic to handle race conditions
     * When multiple requests try to create the same user simultaneously
     * Uses synchronized block and EntityManager flush to ensure data consistency
     */
    private User createUserWithRetry(Auth0UserInfo userInfo, String auth0Id, String email) {
        int maxRetries = 5;
        long retryDelayMs = 200; // 200ms base delay between retries
        
        // Use synchronized block to prevent concurrent creation attempts
        synchronized (("createUser_" + auth0Id).intern()) {
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    // Flush entity manager to ensure we see latest data from database
                    entityManager.flush();
                    entityManager.clear();
                    
                    // Before creating, check again if user was created by another thread
                    User existingUser = userRepository.findByAuth0Id(auth0Id)
                            .orElseGet(() -> {
                                if (email != null && !email.isEmpty()) {
                                    return userRepository.findByEmail(email).orElse(null);
                                }
                                return null;
                            });
                    
                    if (existingUser != null) {
                        log.info("User found after retry attempt {}: auth0Id={}, email={}", attempt, auth0Id, email);
                        return existingUser;
                    }
                    
                    // Try to create new user
                    User newUser = createNewUSer(userInfo);
                    // Flush immediately to commit the transaction
                    entityManager.flush();
                    entityManager.refresh(newUser);
                    log.info("Successfully created user on attempt {}: auth0Id={}, email={}", attempt, auth0Id, email);
                    return newUser;
                    
                } catch (org.springframework.dao.DataIntegrityViolationException ex) {
                    // Duplicate key exception - another thread created the user
                    log.warn("Duplicate user detected on attempt {}: auth0Id={}, email={}, error={}", 
                            attempt, auth0Id, email, ex.getMessage());
                    
                    // Flush and clear to see latest data
                    entityManager.flush();
                    entityManager.clear();
                    
                    // Wait a bit before retrying to allow the other transaction to commit
                    if (attempt < maxRetries) {
                        try {
                            Thread.sleep(retryDelayMs * attempt); // Exponential backoff: 200ms, 400ms, 600ms, 800ms, 1000ms
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            log.error("Thread interrupted during retry delay");
                        }
                        
                        // Flush again after waiting
                        entityManager.flush();
                        entityManager.clear();
                        
                        // Try to find the user that was created by another thread
                        User foundUser = userRepository.findByAuth0Id(auth0Id)
                                .orElseGet(() -> {
                                    if (email != null && !email.isEmpty()) {
                                        return userRepository.findByEmail(email).orElse(null);
                                    }
                                    return null;
                                });
                        
                        if (foundUser != null) {
                            log.info("User found after duplicate key exception on attempt {}: auth0Id={}, email={}", 
                                    attempt, auth0Id, email);
                            return foundUser;
                        }
                    } else {
                        // Last attempt failed, try one more time to find user with longer delay
                        log.warn("Max retries reached, attempting final lookup with extended delay: auth0Id={}, email={}", auth0Id, email);
                        try {
                            Thread.sleep(retryDelayMs * 2); // Wait a bit longer for final attempt
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                        
                        entityManager.flush();
                        entityManager.clear();
                        
                        User finalUser = userRepository.findByAuth0Id(auth0Id)
                                .orElseGet(() -> {
                                    if (email != null && !email.isEmpty()) {
                                        return userRepository.findByEmail(email).orElse(null);
                                    }
                                    return null;
                                });
                        
                        if (finalUser != null) {
                            log.info("User found on final lookup: auth0Id={}, email={}", auth0Id, email);
                            return finalUser;
                        }
                        
                        log.error("Failed to create or find user after {} attempts: auth0Id={}, email={}", 
                                maxRetries, auth0Id, email);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                    }
                } catch (AppException ex) {
                    // Re-throw AppException
                    throw ex;
                } catch (Exception ex) {
                    log.error("Error creating user on attempt {}: auth0Id={}, email={}, error={}", 
                            attempt, auth0Id, email, ex.getMessage(), ex);
                    if (attempt == maxRetries) {
                        throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
                    }
                    // Wait before retry
                    try {
                        Thread.sleep(retryDelayMs * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
                    }
                }
            }
        }
        
        // Should not reach here, but just in case
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    private User createNewUSer(Auth0UserInfo userInfo) {
        String auth0Id = userInfo.getSub();
        String firstName = userInfo.getGivenName() != null ? userInfo.getGivenName() : "";
        String lastName = userInfo.getFamilyName() != null ? userInfo.getFamilyName() : "";
        String email = userInfo.getEmail();
        String phoneNumber = userInfo.getPhoneNumber() != null ? userInfo.getPhoneNumber() : "";
        
        // Ensure email is not null or empty - use fallback if needed
        if (email == null || email.isEmpty()) {
            // Try to extract email from auth0Id or use preferred_username
            if (userInfo.getPreferredUsername() != null && !userInfo.getPreferredUsername().isEmpty()) {
                email = userInfo.getPreferredUsername();
            } else if (auth0Id != null && auth0Id.contains("@")) {
                // Some Auth0 IDs contain email
                email = auth0Id;
            } else {
                // Last resort: use auth0Id as email placeholder (will need to be updated later)
                email = auth0Id + "@auth0.temp";
                log.warn("Email not found in Auth0 user info, using temporary email: auth0Id={}, email={}", auth0Id, email);
            }
        }
        
        String fullName = firstName + " " + lastName;
        
        // Trim fullName to remove extra spaces if firstName or lastName is empty
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            fullName = userInfo.getName() != null ? userInfo.getName() : email;
        }

        User newUser = User.builder()
                .fullName(fullName)
                .email(email)
                .auth0Id(auth0Id)
                .phoneNumber(phoneNumber.isEmpty() ? null : phoneNumber)
                .hireDate(java.time.LocalDate.now())
                .status("ACTIVE")
                .build();

        userRepository.save(newUser);
        log.info("Successfully created new user: auth0Id={}, email={}, fullName={}", auth0Id, email, fullName);
        return newUser;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse getMyInfo() {
        String email = getCurrentEmail();
        if (email == null || email.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_IS_EMPTY);
        }
        
        // Get user info from JWT
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String auth0Id = jwt.getClaim("sub");
        
        // First, try to find existing user
        User user = userRepository.findByAuth0Id(auth0Id)
                .orElseGet(() -> {
                    if (email != null && !email.isEmpty()) {
                        return userRepository.findByEmail(email).orElse(null);
                    }
                    return null;
                });
        
        // If user not found, try to create new user with retry logic
        if (user == null) {
            user = createUserFromJwtWithRetry(jwt, auth0Id, email);
        }
        
        // Update user info from JWT if user exists but auth0Id is missing or different
        boolean needsUpdate = false;
        if (user.getAuth0Id() == null || !user.getAuth0Id().equals(auth0Id)) {
            user.setAuth0Id(auth0Id);
            needsUpdate = true;
        }
        
        // Update email if it's missing or different
        if ((user.getEmail() == null || user.getEmail().isEmpty()) && email != null && !email.isEmpty()) {
            user.setEmail(email);
            needsUpdate = true;
        }
        
        // Save once if any update is needed
        if (needsUpdate) {
            userRepository.save(user);
            entityManager.flush();
            log.info("Updated user info in getMyInfo: auth0Id={}, email={}", auth0Id, email);
        }
        
        return userMapper.toUserResponse(user);
    }
    
    /**
     * Create user from JWT with retry logic to handle race conditions
     * Uses synchronized block and EntityManager flush to ensure data consistency
     */
    private User createUserFromJwtWithRetry(Jwt jwt, String auth0Id, String email) {
        int maxRetries = 5;
        long retryDelayMs = 200; // 200ms base delay between retries
        
        // Use synchronized block to prevent concurrent creation attempts
        synchronized (("createUser_" + auth0Id).intern()) {
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    // Flush entity manager to ensure we see latest data from database
                    entityManager.flush();
                    entityManager.clear();
                    
                    // Before creating, check again if user was created by another thread
                    User existingUser = userRepository.findByAuth0Id(auth0Id)
                            .orElseGet(() -> {
                                if (email != null && !email.isEmpty()) {
                                    return userRepository.findByEmail(email).orElse(null);
                                }
                                return null;
                            });
                    
                    if (existingUser != null) {
                        log.info("User found after retry attempt {} in getMyInfo: auth0Id={}, email={}", attempt, auth0Id, email);
                        return existingUser;
                    }
                    
                    // Get user info from JWT to create user
                    String name = jwt.getClaim("name");
                    String givenName = jwt.getClaim("given_name");
                    String familyName = jwt.getClaim("family_name");
                    
                    String firstName = givenName != null ? givenName : "";
                    String lastName = familyName != null ? familyName : "";
                    String fullName = firstName + " " + lastName;
                    fullName = fullName.trim();
                    if (fullName.isEmpty()) {
                        fullName = name != null ? name : email;
                    }
                    
                    User newUser = User.builder()
                            .fullName(fullName)
                            .email(email)
                            .auth0Id(auth0Id)
                            .hireDate(java.time.LocalDate.now())
                            .status("ACTIVE")
                            .build();
                    
                    userRepository.save(newUser);
                    // Flush immediately to commit the transaction
                    entityManager.flush();
                    entityManager.refresh(newUser);
                    log.info("Successfully created new user from JWT on attempt {}: auth0Id={}, email={}, fullName={}", 
                            attempt, auth0Id, email, fullName);
                    return newUser;
                    
                } catch (org.springframework.dao.DataIntegrityViolationException ex) {
                    // Duplicate key exception - another thread created the user
                    log.warn("Duplicate user detected in getMyInfo on attempt {}: auth0Id={}, email={}, error={}", 
                            attempt, auth0Id, email, ex.getMessage());
                    
                    // Flush and clear to see latest data
                    entityManager.flush();
                    entityManager.clear();
                    
                    // Wait a bit before retrying to allow the other transaction to commit
                    if (attempt < maxRetries) {
                        try {
                            Thread.sleep(retryDelayMs * attempt); // Exponential backoff: 200ms, 400ms, 600ms, 800ms, 1000ms
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            log.error("Thread interrupted during retry delay in getMyInfo");
                        }
                        
                        // Flush again after waiting
                        entityManager.flush();
                        entityManager.clear();
                        
                        // Try to find the user that was created by another thread
                        User foundUser = userRepository.findByAuth0Id(auth0Id)
                                .orElseGet(() -> {
                                    if (email != null && !email.isEmpty()) {
                                        return userRepository.findByEmail(email).orElse(null);
                                    }
                                    return null;
                                });
                        
                        if (foundUser != null) {
                            log.info("User found after duplicate key exception in getMyInfo on attempt {}: auth0Id={}, email={}", 
                                    attempt, auth0Id, email);
                            return foundUser;
                        }
                    } else {
                        // Last attempt failed, try one more time to find user with longer delay
                        log.warn("Max retries reached in getMyInfo, attempting final lookup with extended delay: auth0Id={}, email={}", auth0Id, email);
                        try {
                            Thread.sleep(retryDelayMs * 2); // Wait a bit longer for final attempt
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                        
                        entityManager.flush();
                        entityManager.clear();
                        
                        User finalUser = userRepository.findByAuth0Id(auth0Id)
                                .orElseGet(() -> {
                                    if (email != null && !email.isEmpty()) {
                                        return userRepository.findByEmail(email).orElse(null);
                                    }
                                    return null;
                                });
                        
                        if (finalUser != null) {
                            log.info("User found on final lookup in getMyInfo: auth0Id={}, email={}", auth0Id, email);
                            return finalUser;
                        }
                        
                        log.error("Failed to create or find user after {} attempts in getMyInfo: auth0Id={}, email={}", 
                                maxRetries, auth0Id, email);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                    }
                } catch (AppException ex) {
                    // Re-throw AppException
                    throw ex;
                } catch (Exception ex) {
                    log.error("Error creating user from JWT in getMyInfo on attempt {}: auth0Id={}, email={}, error={}", 
                            attempt, auth0Id, email, ex.getMessage(), ex);
                    if (attempt == maxRetries) {
                        throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
                    }
                    // Wait before retry
                    try {
                        Thread.sleep(retryDelayMs * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR);
                    }
                }
            }
        }
        
        // Should not reach here, but just in case
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private String getCurrentEmail() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("email");
    }


}