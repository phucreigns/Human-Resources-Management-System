package com.phuc.payroll.service;

import com.phuc.payroll.dto.request.PayrollCreateRequest;
import com.phuc.payroll.dto.request.PayrollUpdateRequest;
import com.phuc.payroll.dto.response.PayrollResponse;

import java.util.List;

public interface PayrollService {
    List<PayrollResponse> getAllPayrolls();
    PayrollResponse getPayrollById(Long payrollId);
    List<PayrollResponse> getPayrollsByUserId(Long userId);
    List<PayrollResponse> getPayrollsByPeriod(String payrollPeriod);
    List<PayrollResponse> getPayrollsByUserIdAndPeriod(Long userId, String payrollPeriod);
    List<PayrollResponse> getPayrollsByStatus(String status);
    PayrollResponse createPayroll(PayrollCreateRequest request);
    PayrollResponse updatePayroll(Long payrollId, PayrollUpdateRequest request);
    void deletePayroll(Long payrollId);
}

