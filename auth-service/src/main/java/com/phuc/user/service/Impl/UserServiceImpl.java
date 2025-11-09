package com.phuc.user.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuc.user.dto.auth0.Auth0TokenResponse;
import com.phuc.user.dto.auth0.Auth0UserInfo;
import com.phuc.user.dto.request.RefreshTokenRequest;
import com.phuc.user.dto.request.UserUpdateRequest;
import com.phuc.user.dto.response.TokenResponse;
import com.phuc.user.dto.response.UserResponse;
import com.phuc.user.entity.User;
import com.phuc.user.exception.AppException;
import com.phuc.user.exception.ErrorCode;
import com.phuc.user.httpclient.Auth0Client;
import com.phuc.user.mapper.UserMapper;
import com.phuc.user.repository.UserRepository;
import com.phuc.user.service.UserService;
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
            userRepository.findByEmail(userInfo.getEmail()).orElseGet(() -> createNewUser(userInfo));

            TokenResponse response = TokenResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .expiresIn(tokenResponse.getExpiresIn() != null ? tokenResponse.getExpiresIn().toString() : null)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage(), e);
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
            body.put("connection", "employeename-Password-Authentication");

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
     * Tạo user mới từ thông tin Auth0.
     * 
     * CÁCH 5: Không cần company_id ngay
     * - Tạo user với company_id = NULL
     * - Admin sẽ set company_id sau khi activate user
     * - Ưu điểm: Linh hoạt nhất, không phụ thuộc vào Auth0 metadata
     * 
     * Status mặc định: "PENDING" - User cần được admin activate và set company_id trước khi sử dụng hệ thống
     */
    private User createNewUser(Auth0UserInfo userInfo) {
        String auth0Id = userInfo.getSub();
        String firstName = userInfo.getGivenName() != null ? userInfo.getGivenName() : "";
        String lastName = userInfo.getFamilyName() != null ? userInfo.getFamilyName() : "";
        String email = userInfo.getEmail();
        String phoneNumber = userInfo.getPhoneNumber() != null ? userInfo.getPhoneNumber() : "";
        String fullName = firstName + " " + lastName;

        // Tạo user với company_id = NULL
        // Admin sẽ set company_id sau khi activate user
        User newUser = User.builder()
                .fullName(fullName)
                .email(email)
                .auth0Id(auth0Id)
                .phone(phoneNumber)
                .companyId(null) // Company ID sẽ được admin set sau khi activate
                .status("PENDING") // Status mặc định: PENDING - cần admin activate và set company_id
                .build();

        userRepository.save(newUser);
        log.info("Created new user with email: {}, companyId: NULL (admin will set later), status: PENDING", email);
        return newUser;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        userMapper.updateUser(user, request);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse activateUser(com.phuc.user.dto.request.UserActivateRequest request, Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        
        // Set company_id và activate user
        user.setCompanyId(request.getCompanyId());
        user.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");
        
        userRepository.save(user);
        log.info("Activated user with ID: {}, companyId: {}, status: {}", 
                userId, request.getCompanyId(), user.getStatus());
        
        return userMapper.toUserResponse(user);
    }

    private String getCurrentEmail() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("email");
    }


}

