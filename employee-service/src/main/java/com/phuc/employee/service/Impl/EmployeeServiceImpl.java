package com.phuc.employee.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuc.employee.dto.auth0.Auth0TokenResponse;
import com.phuc.employee.dto.auth0.Auth0EmployeeInfo;
import com.phuc.employee.dto.request.RefreshTokenRequest;
import com.phuc.employee.dto.request.EmployeeUpdateRequest;
import com.phuc.employee.dto.response.TokenResponse;
import com.phuc.employee.dto.response.EmployeeResponse;
import com.phuc.employee.entity.Employee;
import com.phuc.employee.exception.AppException;
import com.phuc.employee.exception.ErrorCode;
import com.phuc.employee.httpclient.Auth0Client;
import com.phuc.employee.mapper.EmployeeMapper;
import com.phuc.employee.repository.EmployeeRepository;
import com.phuc.employee.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
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

            Auth0EmployeeInfo employeeInfo = auth0Client.getEmployeeInfo("Bearer " + tokenResponse.getAccessToken());
            employeeRepository.findByEmail(employeeInfo.getEmail()).orElseGet(() -> createNewEmployee(employeeInfo));

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



    private Employee createNewEmployee(Auth0EmployeeInfo EmployeeInfo) {
        String auth0Id = EmployeeInfo.getSub();
        String firstName = EmployeeInfo.getGivenName() != null ? EmployeeInfo.getGivenName() : "";
        String lastName = EmployeeInfo.getFamilyName() != null ? EmployeeInfo.getFamilyName() : "";
        String email = EmployeeInfo.getEmail();
        String phoneNumber = EmployeeInfo.getPhoneNumber() != null ? EmployeeInfo.getPhoneNumber() : "";
        String fullName = firstName + " " + lastName;

        Employee newEmployee = Employee.builder()
                .fullName(fullName)
                .email(email)
                .auth0Id(auth0Id)
                .phone(phoneNumber)
                .companyId(1L) // Default company ID - should be set from Auth0 metadata or config
                .status("ACTIVE") // Default status
                .build();

        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findByEmployeeId(id).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return employeeMapper.toEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return employeeMapper.toEmployeeResponse(employee);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeUpdateRequest request, Long EmployeeId) {
        Employee employee = employeeRepository.findByEmployeeId(EmployeeId).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        employeeMapper.updateEmployee(employee, request);
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponse(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private String getCurrentEmail() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("email");
    }


}
