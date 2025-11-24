package com.phuc.timetracking.dto.response;

import com.phuc.timetracking.enums.LeaveRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveRequestResponse {

    Long leaveRequestId;
    String userId;
    LeaveTypeResponse leaveType;
    LocalDate startDate;
    LocalDate endDate;
    Integer totalDays;
    LeaveRequestStatus status;
    String approvedBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

