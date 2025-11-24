package com.phuc.payroll.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayrollAdjustmentResponse {
    Long adjustmentId;
    Long payrollId;
    Long userId;
    String adjustmentType;
    Double amount;
    String description;
    LocalDate effectiveDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

