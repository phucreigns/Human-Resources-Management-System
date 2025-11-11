package com.phuc.timetracking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveTypeResponse {

    Long leaveTypeId;
    String leaveTypeName;
    String leaveTypeCode;
    Integer maxDaysPerYear;
    Boolean requiresApproval;
    Boolean isPaid;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

