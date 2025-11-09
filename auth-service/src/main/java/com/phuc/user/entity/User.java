package com.phuc.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "company_id")
    Long companyId; 
    
    @Column(name = "department_id")
    Long departmentId;

    @Column(name = "position_id")
    Long positionId;

    @Column(name = "manager_id")
    Long managerId;

    @Column(name = "employee_code", length = 50, unique = true)
    String employeeCode;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Column(name = "email", length = 255, unique = true)
    String email;

    @Column(name = "auth0_id", length = 100, unique = true)
    String auth0Id;

    @Column(name = "phone", length = 30)
    String phone;

    @Column(name = "hire_date")
    LocalDate hireDate;

    @Column(name = "status", length = 50)
    String status;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

