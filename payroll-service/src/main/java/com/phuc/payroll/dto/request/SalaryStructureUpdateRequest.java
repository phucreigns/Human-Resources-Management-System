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
public class SalaryStructureUpdateRequest {

    @Positive(message = "Base salary must be positive")
    Double baseSalary;

    Map<String, Object> salaryComponents;

    @Positive(message = "Total salary must be positive")
    Double totalSalary;

    LocalDate effectiveFrom;

    Boolean isActive;
}





