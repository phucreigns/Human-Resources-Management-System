package com.phuc.payroll.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_id")
    Long payrollId;

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "payroll_period", nullable = false, length = 50)
    String payrollPeriod;

    @Column(name = "pay_date", nullable = false)
    LocalDate payDate;

    @Column(name = "gross_salary", nullable = false)
    Double grossSalary;

    @Column(name = "net_salary", nullable = false)
    Double netSalary;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "deductions", columnDefinition = "jsonb")
    Map<String, Object> deductions;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "bonuses", columnDefinition = "jsonb")
    Map<String, Object> bonuses;

    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    String status = "PENDING";

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

