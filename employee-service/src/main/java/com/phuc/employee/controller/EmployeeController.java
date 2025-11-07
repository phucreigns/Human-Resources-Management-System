package com.phuc.employee.controller;

import com.phuc.employee.dto.ApiResponse;
import com.phuc.employee.dto.request.EmployeeUpdateRequest;
import com.phuc.employee.dto.response.EmployeeResponse;
import com.phuc.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phuc.employee.dto.response.TokenResponse;
import com.phuc.employee.dto.request.RefreshTokenRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping("/auth/login")
    public ResponseEntity<TokenResponse> login(@RequestParam(required = false) String code) {
        return employeeService.login(code);
    }

    @PostMapping("/auth/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return ApiResponse.<TokenResponse>builder()
                .result(employeeService.refreshToken(request))
                .build();
    }

    @PutMapping("/reset-password")
    public ApiResponse<Void> resetPassword() {
        employeeService.resetPassword();
        return ApiResponse.<Void>builder()
                .message("Send reset password email successfully")
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return employeeService.logout();
    }

    @GetMapping("/id/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/email/{email}")
    public EmployeeResponse getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @GetMapping("/all")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/update/{employeeId}")
    public EmployeeResponse updateEmployee(@RequestBody @Valid EmployeeUpdateRequest request, @PathVariable Long employeeId){
        return employeeService.updateEmployee(request, employeeId);
    }

    @DeleteMapping("/{employeeId}") 
    public void deleteEmployeeById(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }



}

