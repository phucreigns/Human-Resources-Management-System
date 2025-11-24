package com.phuc.auth.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.phuc.auth.dto.ApiResponse;
import com.phuc.auth.dto.request.RefreshTokenRequest;
import com.phuc.auth.dto.request.UserUpdateRequest;
import com.phuc.auth.dto.response.TokenResponse;
import com.phuc.auth.dto.response.UserResponse;
import com.phuc.auth.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam(required = false) String code) {
        return userService.login(code);
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return ApiResponse.<TokenResponse>builder()
                .result(userService.refreshToken(request))
                .build();
    }

    @PutMapping("/reset-password")
    public ApiResponse<Void> resetPassword() {
        userService.resetPassword();
        return ApiResponse.<Void>builder()
                .message("Send reset password email successfully")
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return userService.logout();
    }

    @GetMapping("/id/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public ApiResponse<UserResponse> getUserByEmail(@PathVariable String email) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByEmail(email))
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @PutMapping("/update/{userId}")
    public ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request, @PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(request, userId))
                .message("User updated successfully")
                .build();
    }
    
    @PutMapping("/me")
    public ApiResponse<UserResponse> updateMyProfile(@RequestBody @Valid UserUpdateRequest request){
        String email = ((org.springframework.security.oauth2.jwt.Jwt) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("email");
        UserResponse currentUser = userService.getUserByEmail(email);
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(request, currentUser.getUserId()))
                .message("Profile updated successfully")
                .build();
    }

    @DeleteMapping("/{userId}") 
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}

