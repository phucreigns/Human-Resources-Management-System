package com.phuc.payroll.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payroll_adjustments")
public class PayrollAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adjustment_id")
    Long adjustmentId;

    @Column(name = "payroll_id", nullable = false)
    Long payrollId;

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "adjustment_type", nullable = false, length = 50)
    String adjustmentType;

    @Column(name = "amount", nullable = false)
    Double amount;

    @Column(name = "description", length = 1000)
    String description;

    @Column(name = "effective_date", nullable = false)
    LocalDate effectiveDate;

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

