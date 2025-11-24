package com.phuc.payroll.controller;

import com.phuc.payroll.dto.ApiResponse;
import com.phuc.payroll.dto.request.PayrollCreateRequest;
import com.phuc.payroll.dto.request.PayrollUpdateRequest;
import com.phuc.payroll.dto.response.PayrollResponse;
import com.phuc.payroll.service.PayrollService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payrolls")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollController {

    PayrollService payrollService;

    @GetMapping
    public ApiResponse<List<PayrollResponse>> getAllPayrolls() {
        return ApiResponse.<List<PayrollResponse>>builder()
                .result(payrollService.getAllPayrolls())
                .message("Payrolls retrieved successfully")
                .build();
    }

    @GetMapping("/{payrollId}")
    public ApiResponse<PayrollResponse> getPayrollById(@PathVariable Long payrollId) {
        return ApiResponse.<PayrollResponse>builder()
                .result(payrollService.getPayrollById(payrollId))
                .message("Payroll retrieved successfully")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<PayrollResponse>> getPayrollsByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<PayrollResponse>>builder()
                .result(payrollService.getPayrollsByUserId(userId))
                .message("Payrolls retrieved successfully")
                .build();
    }

    @GetMapping("/period/{payrollPeriod}")
    public ApiResponse<List<PayrollResponse>> getPayrollsByPeriod(@PathVariable String payrollPeriod) {
        return ApiResponse.<List<PayrollResponse>>builder()
                .result(payrollService.getPayrollsByPeriod(payrollPeriod))
                .message("Payrolls retrieved successfully")
                .build();
    }

    @GetMapping("/user/{userId}/period/{payrollPeriod}")
    public ApiResponse<List<PayrollResponse>> getPayrollsByUserIdAndPeriod(
            @PathVariable Long userId,
            @PathVariable String payrollPeriod) {
        return ApiResponse.<List<PayrollResponse>>builder()
                .result(payrollService.getPayrollsByUserIdAndPeriod(userId, payrollPeriod))
                .message("Payrolls retrieved successfully")
                .build();
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<PayrollResponse>> getPayrollsByStatus(@PathVariable String status) {
        return ApiResponse.<List<PayrollResponse>>builder()
                .result(payrollService.getPayrollsByStatus(status))
                .message("Payrolls retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PayrollResponse> createPayroll(@RequestBody @Valid PayrollCreateRequest request) {
        return ApiResponse.<PayrollResponse>builder()
                .result(payrollService.createPayroll(request))
                .message("Payroll created successfully")
                .build();
    }

    @PutMapping("/{payrollId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PayrollResponse> updatePayroll(
            @PathVariable Long payrollId,
            @RequestBody @Valid PayrollUpdateRequest request) {
        return ApiResponse.<PayrollResponse>builder()
                .result(payrollService.updatePayroll(payrollId, request))
                .message("Payroll updated successfully")
                .build();
    }

    @DeleteMapping("/{payrollId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long payrollId) {
        payrollService.deletePayroll(payrollId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

