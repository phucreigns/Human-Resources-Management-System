package com.phuc.payroll.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryStructureCreateRequest {

    @NotNull(message = "User ID is required")
    Long userId;

    @NotNull(message = "Base salary is required")
    @Positive(message = "Base salary must be positive")
    Double baseSalary;

    Map<String, Object> salaryComponents;

    @NotNull(message = "Total salary is required")
    @Positive(message = "Total salary must be positive")
    Double totalSalary;

    @NotNull(message = "Effective from date is required")
    LocalDate effectiveFrom;

    Boolean isActive;
}

