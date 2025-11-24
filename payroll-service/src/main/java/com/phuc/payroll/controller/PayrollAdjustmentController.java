package com.phuc.payroll.controller;

import com.phuc.payroll.dto.ApiResponse;
import com.phuc.payroll.dto.request.PayrollAdjustmentCreateRequest;
import com.phuc.payroll.dto.request.PayrollAdjustmentUpdateRequest;
import com.phuc.payroll.dto.response.PayrollAdjustmentResponse;
import com.phuc.payroll.service.PayrollAdjustmentService;
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
@RequestMapping("/payroll-adjustments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollAdjustmentController {

    PayrollAdjustmentService payrollAdjustmentService;

    @GetMapping
    public ApiResponse<List<PayrollAdjustmentResponse>> getAllPayrollAdjustments() {
        return ApiResponse.<List<PayrollAdjustmentResponse>>builder()
                .result(payrollAdjustmentService.getAllPayrollAdjustments())
                .message("Payroll adjustments retrieved successfully")
                .build();
    }

    @GetMapping("/{adjustmentId}")
    public ApiResponse<PayrollAdjustmentResponse> getPayrollAdjustmentById(@PathVariable Long adjustmentId) {
        return ApiResponse.<PayrollAdjustmentResponse>builder()
                .result(payrollAdjustmentService.getPayrollAdjustmentById(adjustmentId))
                .message("Payroll adjustment retrieved successfully")
                .build();
    }

    @GetMapping("/payroll/{payrollId}")
    public ApiResponse<List<PayrollAdjustmentResponse>> getPayrollAdjustmentsByPayrollId(@PathVariable Long payrollId) {
        return ApiResponse.<List<PayrollAdjustmentResponse>>builder()
                .result(payrollAdjustmentService.getPayrollAdjustmentsByPayrollId(payrollId))
                .message("Payroll adjustments retrieved successfully")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<PayrollAdjustmentResponse>> getPayrollAdjustmentsByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<PayrollAdjustmentResponse>>builder()
                .result(payrollAdjustmentService.getPayrollAdjustmentsByUserId(userId))
                .message("Payroll adjustments retrieved successfully")
                .build();
    }

    @GetMapping("/payroll/{payrollId}/user/{userId}")
    public ApiResponse<List<PayrollAdjustmentResponse>> getPayrollAdjustmentsByPayrollIdAndUserId(
            @PathVariable Long payrollId,
            @PathVariable Long userId) {
        return ApiResponse.<List<PayrollAdjustmentResponse>>builder()
                .result(payrollAdjustmentService.getPayrollAdjustmentsByPayrollIdAndUserId(payrollId, userId))
                .message("Payroll adjustments retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PayrollAdjustmentResponse> createPayrollAdjustment(@RequestBody @Valid PayrollAdjustmentCreateRequest request) {
        return ApiResponse.<PayrollAdjustmentResponse>builder()
                .result(payrollAdjustmentService.createPayrollAdjustment(request))
                .message("Payroll adjustment created successfully")
                .build();
    }

    @PutMapping("/{adjustmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PayrollAdjustmentResponse> updatePayrollAdjustment(
            @PathVariable Long adjustmentId,
            @RequestBody @Valid PayrollAdjustmentUpdateRequest request) {
        return ApiResponse.<PayrollAdjustmentResponse>builder()
                .result(payrollAdjustmentService.updatePayrollAdjustment(adjustmentId, request))
                .message("Payroll adjustment updated successfully")
                .build();
    }

    @DeleteMapping("/{adjustmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePayrollAdjustment(@PathVariable Long adjustmentId) {
        payrollAdjustmentService.deletePayrollAdjustment(adjustmentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

