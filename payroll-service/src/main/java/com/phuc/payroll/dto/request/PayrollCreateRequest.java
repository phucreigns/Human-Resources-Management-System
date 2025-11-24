package com.phuc.payroll.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class PayrollCreateRequest {

    @NotNull(message = "User ID is required")
    Long userId;

    @NotBlank(message = "Payroll period is required")
    String payrollPeriod;

    @NotNull(message = "Pay date is required")
    LocalDate payDate;

    @NotNull(message = "Gross salary is required")
    @Positive(message = "Gross salary must be positive")
    Double grossSalary;

    @NotNull(message = "Net salary is required")
    @Positive(message = "Net salary must be positive")
    Double netSalary;

    Map<String, Object> deductions;

    Map<String, Object> bonuses;

    String status;
}

