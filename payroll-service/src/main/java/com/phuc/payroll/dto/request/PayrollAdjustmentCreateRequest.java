package com.phuc.payroll.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayrollAdjustmentCreateRequest {

    @NotNull(message = "Payroll ID is required")
    Long payrollId;

    @NotNull(message = "User ID is required")
    Long userId;

    @NotBlank(message = "Adjustment type is required")
    String adjustmentType;

    @NotNull(message = "Amount is required")
    Double amount;

    String description;

    @NotNull(message = "Effective date is required")
    LocalDate effectiveDate;
}

