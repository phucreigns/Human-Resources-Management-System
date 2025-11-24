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
public class PayrollResponse {
    Long payrollId;
    Long userId;
    String payrollPeriod;
    LocalDate payDate;
    Double grossSalary;
    Double netSalary;
    Map<String, Object> deductions;
    Map<String, Object> bonuses;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

