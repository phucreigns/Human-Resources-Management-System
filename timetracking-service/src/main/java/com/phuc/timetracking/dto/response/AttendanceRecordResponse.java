package com.phuc.timetracking.dto.response;

import com.phuc.timetracking.enums.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRecordResponse {

    Long attendanceId;
    String userId;
    LocalDate attendanceDate;
    LocalTime checkInTime;
    LocalTime checkOutTime;
    Double hoursWorked;
    AttendanceStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

