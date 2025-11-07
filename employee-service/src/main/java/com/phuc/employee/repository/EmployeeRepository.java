package com.phuc.employee.repository;

import com.phuc.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(Long EmployeeId);
    Optional<Employee> findByEmail(String email);
}

