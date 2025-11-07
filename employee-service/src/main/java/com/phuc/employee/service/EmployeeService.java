package com.phuc.employee.service;

import com.phuc.employee.dto.request.EmployeeCreateRequest;
import com.phuc.employee.dto.request.EmployeeUpdateRequest;
import com.phuc.employee.dto.response.EmployeeResponse;
import com.phuc.employee.dto.request.RefreshTokenRequest;
import com.phuc.employee.dto.response.TokenResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface EmployeeService {
    ResponseEntity<TokenResponse> login(String code);

    TokenResponse refreshToken(RefreshTokenRequest request);

    @Transactional
    void resetPassword();

    @Transactional
    ResponseEntity<Void> logout();

    EmployeeResponse getEmployeeById(Long employeeId);

    EmployeeResponse getEmployeeByEmail(String email);

    List<EmployeeResponse> getAllEmployees(); 
    EmployeeResponse updateEmployee(EmployeeUpdateRequest request, Long employeeId);
    void deleteEmployee(Long employeeId);

}
