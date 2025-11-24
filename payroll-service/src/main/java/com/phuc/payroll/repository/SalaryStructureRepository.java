package com.phuc.payroll.repository;

import com.phuc.payroll.entity.SalaryStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, Long> {
    Optional<SalaryStructure> findBySalaryStructureId(Long salaryStructureId);
    List<SalaryStructure> findByUserId(Long userId);
    List<SalaryStructure> findByUserIdAndIsActive(Long userId, Boolean isActive);
    Optional<SalaryStructure> findByUserIdAndIsActiveTrue(Long userId);
}

