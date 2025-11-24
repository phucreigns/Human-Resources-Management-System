package com.phuc.payroll.service.impl;

import com.phuc.payroll.dto.request.PayrollCreateRequest;
import com.phuc.payroll.dto.request.PayrollUpdateRequest;
import com.phuc.payroll.dto.response.PayrollResponse;
import com.phuc.payroll.entity.Payroll;
import com.phuc.payroll.exception.AppException;
import com.phuc.payroll.exception.ErrorCode;
import com.phuc.payroll.mapper.PayrollMapper;
import com.phuc.payroll.repository.PayrollRepository;
import com.phuc.payroll.service.PayrollService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollServiceImpl implements PayrollService {

    PayrollRepository payrollRepository;
    PayrollMapper payrollMapper;

    @Override
    public List<PayrollResponse> getAllPayrolls() {
        List<Payroll> payrolls = payrollRepository.findAll();
        return payrolls.stream()
                .map(payrollMapper::toPayrollResponse)
                .toList();
    }

    @Override
    public PayrollResponse getPayrollById(Long payrollId) {
        Payroll payroll = payrollRepository.findByPayrollId(payrollId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_NOT_FOUND));
        return payrollMapper.toPayrollResponse(payroll);
    }

    @Override
    public List<PayrollResponse> getPayrollsByUserId(Long userId) {
        List<Payroll> payrolls = payrollRepository.findByUserId(userId);
        return payrolls.stream()
                .map(payrollMapper::toPayrollResponse)
                .toList();
    }

    @Override
    public List<PayrollResponse> getPayrollsByPeriod(String payrollPeriod) {
        List<Payroll> payrolls = payrollRepository.findByPayrollPeriod(payrollPeriod);
        return payrolls.stream()
                .map(payrollMapper::toPayrollResponse)
                .toList();
    }

    @Override
    public List<PayrollResponse> getPayrollsByUserIdAndPeriod(Long userId, String payrollPeriod) {
        List<Payroll> payrolls = payrollRepository.findByUserIdAndPayrollPeriod(userId, payrollPeriod);
        return payrolls.stream()
                .map(payrollMapper::toPayrollResponse)
                .toList();
    }

    @Override
    public List<PayrollResponse> getPayrollsByStatus(String status) {
        List<Payroll> payrolls = payrollRepository.findByStatus(status);
        return payrolls.stream()
                .map(payrollMapper::toPayrollResponse)
                .toList();
    }

    @Override
    @Transactional
    public PayrollResponse createPayroll(PayrollCreateRequest request) {
        Payroll payroll = payrollMapper.toPayroll(request);
        Payroll savedPayroll = payrollRepository.save(payroll);
        log.info("Created payroll with ID: {}", savedPayroll.getPayrollId());
        return payrollMapper.toPayrollResponse(savedPayroll);
    }

    @Override
    @Transactional
    public PayrollResponse updatePayroll(Long payrollId, PayrollUpdateRequest request) {
        Payroll payroll = payrollRepository.findByPayrollId(payrollId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_NOT_FOUND));

        payrollMapper.updatePayroll(payroll, request);
        Payroll updatedPayroll = payrollRepository.save(payroll);
        log.info("Updated payroll with ID: {}", payrollId);
        return payrollMapper.toPayrollResponse(updatedPayroll);
    }

    @Override
    @Transactional
    public void deletePayroll(Long payrollId) {
        Payroll payroll = payrollRepository.findByPayrollId(payrollId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_NOT_FOUND));
        payrollRepository.delete(payroll);
        log.info("Deleted payroll with ID: {}", payrollId);
    }
}

