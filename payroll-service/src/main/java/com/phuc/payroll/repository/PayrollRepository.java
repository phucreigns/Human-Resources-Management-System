package com.phuc.payroll.repository;

import com.phuc.payroll.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Optional<Payroll> findByPayrollId(Long payrollId);
    List<Payroll> findByUserId(Long userId);
    List<Payroll> findByPayrollPeriod(String payrollPeriod);
    List<Payroll> findByUserIdAndPayrollPeriod(Long userId, String payrollPeriod);
    List<Payroll> findByStatus(String status);
}

