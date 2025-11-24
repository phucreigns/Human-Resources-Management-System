package com.phuc.payroll.repository;

import com.phuc.payroll.entity.PayrollAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollAdjustmentRepository extends JpaRepository<PayrollAdjustment, Long> {
    Optional<PayrollAdjustment> findByAdjustmentId(Long adjustmentId);
    List<PayrollAdjustment> findByPayrollId(Long payrollId);
    List<PayrollAdjustment> findByUserId(Long userId);
    List<PayrollAdjustment> findByPayrollIdAndUserId(Long payrollId, Long userId);
}

