package com.phuc.timetracking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveTypeCreateRequest {

    @NotBlank(message = "Leave type name is required")
    String leaveTypeName;

    @NotBlank(message = "Leave type code is required")
    String leaveTypeCode;

    @NotNull(message = "Max days per year is required")
    @Positive(message = "Max days per year must be positive")
    Integer maxDaysPerYear;

    @NotNull(message = "Requires approval is required")
    Boolean requiresApproval;

    @NotNull(message = "Is paid is required")
    Boolean isPaid;
}

