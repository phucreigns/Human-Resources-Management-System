package com.phuc.timetracking.dto.request;

import com.phuc.timetracking.enums.LeaveRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveRequestUpdateRequest {

    Long leaveTypeId;

    LocalDate startDate;

    LocalDate endDate;

    LeaveRequestStatus status;

    String approvedBy;
}

