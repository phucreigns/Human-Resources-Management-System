package com.phuc.employee.entity;

import java.math.BigDecimal;

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
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    Long positionId;

    @Column(name = "department_id", nullable = false)
    Long departmentId;

    @Column(name = "position_name", length = 255, nullable = false)
    String positionName;

    @Column(name = "position_code", length = 50, unique = true)
    String positionCode;

    @Column(name = "salary_min")
    BigDecimal salaryMin;

    @Column(name = "salary_max")
    BigDecimal salaryMax;
}


