package com.phuc.user.service;

import com.phuc.user.dto.request.UserUpdateRequest;
import com.phuc.user.dto.response.UserResponse;
import com.phuc.user.dto.request.RefreshTokenRequest;
import com.phuc.user.dto.response.TokenResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserService {
    ResponseEntity<TokenResponse> login(String code);

    TokenResponse refreshToken(RefreshTokenRequest request);

    @Transactional
    void resetPassword();

    @Transactional
    ResponseEntity<Void> logout();

    UserResponse getUserById(Long userId);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getAllUsers(); 
    UserResponse updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    
    @Transactional
    UserResponse activateUser(com.phuc.user.dto.request.UserActivateRequest request, Long userId);

}

