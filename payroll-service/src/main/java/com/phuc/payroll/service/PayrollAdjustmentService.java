package com.phuc.payroll.service;

import com.phuc.payroll.dto.request.PayrollAdjustmentCreateRequest;
import com.phuc.payroll.dto.request.PayrollAdjustmentUpdateRequest;
import com.phuc.payroll.dto.response.PayrollAdjustmentResponse;

import java.util.List;

public interface PayrollAdjustmentService {
    List<PayrollAdjustmentResponse> getAllPayrollAdjustments();
    PayrollAdjustmentResponse getPayrollAdjustmentById(Long adjustmentId);
    List<PayrollAdjustmentResponse> getPayrollAdjustmentsByPayrollId(Long payrollId);
    List<PayrollAdjustmentResponse> getPayrollAdjustmentsByUserId(Long userId);
    List<PayrollAdjustmentResponse> getPayrollAdjustmentsByPayrollIdAndUserId(Long payrollId, Long userId);
    PayrollAdjustmentResponse createPayrollAdjustment(PayrollAdjustmentCreateRequest request);
    PayrollAdjustmentResponse updatePayrollAdjustment(Long adjustmentId, PayrollAdjustmentUpdateRequest request);
    void deletePayrollAdjustment(Long adjustmentId);
}

