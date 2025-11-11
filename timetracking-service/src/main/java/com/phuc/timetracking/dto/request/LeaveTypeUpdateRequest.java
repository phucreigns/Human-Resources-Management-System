package com.phuc.timetracking.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveTypeUpdateRequest {

    String leaveTypeName;

    String leaveTypeCode;

    @Positive(message = "Max days per year must be positive")
    Integer maxDaysPerYear;

    Boolean requiresApproval;

    Boolean isPaid;
}

