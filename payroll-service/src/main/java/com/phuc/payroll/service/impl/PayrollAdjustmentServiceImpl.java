package com.phuc.payroll.service.impl;

import com.phuc.payroll.dto.request.PayrollAdjustmentCreateRequest;
import com.phuc.payroll.dto.request.PayrollAdjustmentUpdateRequest;
import com.phuc.payroll.dto.response.PayrollAdjustmentResponse;
import com.phuc.payroll.entity.PayrollAdjustment;
import com.phuc.payroll.exception.AppException;
import com.phuc.payroll.exception.ErrorCode;
import com.phuc.payroll.mapper.PayrollAdjustmentMapper;
import com.phuc.payroll.repository.PayrollAdjustmentRepository;
import com.phuc.payroll.repository.PayrollRepository;
import com.phuc.payroll.service.PayrollAdjustmentService;
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
public class PayrollAdjustmentServiceImpl implements PayrollAdjustmentService {

    PayrollAdjustmentRepository payrollAdjustmentRepository;
    PayrollRepository payrollRepository;
    PayrollAdjustmentMapper payrollAdjustmentMapper;

    @Override
    public List<PayrollAdjustmentResponse> getAllPayrollAdjustments() {
        List<PayrollAdjustment> payrollAdjustments = payrollAdjustmentRepository.findAll();
        return payrollAdjustments.stream()
                .map(payrollAdjustmentMapper::toPayrollAdjustmentResponse)
                .toList();
    }

    @Override
    public PayrollAdjustmentResponse getPayrollAdjustmentById(Long adjustmentId) {
        PayrollAdjustment payrollAdjustment = payrollAdjustmentRepository.findByAdjustmentId(adjustmentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_ADJUSTMENT_NOT_FOUND));
        return payrollAdjustmentMapper.toPayrollAdjustmentResponse(payrollAdjustment);
    }

    @Override
    public List<PayrollAdjustmentResponse> getPayrollAdjustmentsByPayrollId(Long payrollId) {
        List<PayrollAdjustment> payrollAdjustments = payrollAdjustmentRepository.findByPayrollId(payrollId);
        return payrollAdjustments.stream()
                .map(payrollAdjustmentMapper::toPayrollAdjustmentResponse)
                .toList();
    }

    @Override
    public List<PayrollAdjustmentResponse> getPayrollAdjustmentsByUserId(Long userId) {
        List<PayrollAdjustment> payrollAdjustments = payrollAdjustmentRepository.findByUserId(userId);
        return payrollAdjustments.stream()
                .map(payrollAdjustmentMapper::toPayrollAdjustmentResponse)
                .toList();
    }

    @Override
    public List<PayrollAdjustmentResponse> getPayrollAdjustmentsByPayrollIdAndUserId(Long payrollId, Long userId) {
        List<PayrollAdjustment> payrollAdjustments = payrollAdjustmentRepository.findByPayrollIdAndUserId(payrollId, userId);
        return payrollAdjustments.stream()
                .map(payrollAdjustmentMapper::toPayrollAdjustmentResponse)
                .toList();
    }

    @Override
    @Transactional
    public PayrollAdjustmentResponse createPayrollAdjustment(PayrollAdjustmentCreateRequest request) {
        // Validate that payroll exists
        payrollRepository.findByPayrollId(request.getPayrollId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_NOT_FOUND));

        PayrollAdjustment payrollAdjustment = payrollAdjustmentMapper.toPayrollAdjustment(request);
        PayrollAdjustment savedPayrollAdjustment = payrollAdjustmentRepository.save(payrollAdjustment);
        log.info("Created payroll adjustment with ID: {}", savedPayrollAdjustment.getAdjustmentId());
        return payrollAdjustmentMapper.toPayrollAdjustmentResponse(savedPayrollAdjustment);
    }

    @Override
    @Transactional
    public PayrollAdjustmentResponse updatePayrollAdjustment(Long adjustmentId, PayrollAdjustmentUpdateRequest request) {
        PayrollAdjustment payrollAdjustment = payrollAdjustmentRepository.findByAdjustmentId(adjustmentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_ADJUSTMENT_NOT_FOUND));

        payrollAdjustmentMapper.updatePayrollAdjustment(payrollAdjustment, request);
        PayrollAdjustment updatedPayrollAdjustment = payrollAdjustmentRepository.save(payrollAdjustment);
        log.info("Updated payroll adjustment with ID: {}", adjustmentId);
        return payrollAdjustmentMapper.toPayrollAdjustmentResponse(updatedPayrollAdjustment);
    }

    @Override
    @Transactional
    public void deletePayrollAdjustment(Long adjustmentId) {
        PayrollAdjustment payrollAdjustment = payrollAdjustmentRepository.findByAdjustmentId(adjustmentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYROLL_ADJUSTMENT_NOT_FOUND));
        payrollAdjustmentRepository.delete(payrollAdjustment);
        log.info("Deleted payroll adjustment with ID: {}", adjustmentId);
    }
}

