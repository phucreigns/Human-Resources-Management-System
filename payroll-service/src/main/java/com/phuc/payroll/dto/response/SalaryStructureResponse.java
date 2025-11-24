package com.phuc.payroll.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryStructureResponse {
    Long salaryStructureId;
    Long userId;
    Double baseSalary;
    Map<String, Object> salaryComponents;
    Double totalSalary;
    LocalDate effectiveFrom;
    Boolean isActive;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

