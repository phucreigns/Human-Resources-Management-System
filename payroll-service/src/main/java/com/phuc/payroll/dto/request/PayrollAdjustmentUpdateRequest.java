package com.phuc.payroll.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayrollAdjustmentUpdateRequest {

    String adjustmentType;

    Double amount;

    String description;

    LocalDate effectiveDate;
}





