package com.phuc.timetracking.dto.request;

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
public class LeaveRequestCreateRequest {

    @NotBlank(message = "Employee ID is required")
    String employeeId;

    @NotNull(message = "Leave type ID is required")
    Long leaveTypeId;

    @NotNull(message = "Start date is required")
    LocalDate startDate;

    @NotNull(message = "End date is required")
    LocalDate endDate;
}

