package com.phuc.timetracking.dto.request;

import com.phuc.timetracking.enums.AttendanceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRecordCreateRequest {

    @NotBlank(message = "Employee ID is required")
    String employeeId;

    @NotNull(message = "Attendance date is required")
    LocalDate attendanceDate;

    LocalTime checkInTime;

    LocalTime checkOutTime;

    @NotNull(message = "Status is required")
    AttendanceStatus status;
}

