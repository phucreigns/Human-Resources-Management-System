package com.phuc.payroll.dto.request;

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
public class PayrollUpdateRequest {

    String payrollPeriod;

    LocalDate payDate;

    @Positive(message = "Gross salary must be positive")
    Double grossSalary;

    @Positive(message = "Net salary must be positive")
    Double netSalary;

    Map<String, Object> deductions;

    Map<String, Object> bonuses;

    String status;
}





