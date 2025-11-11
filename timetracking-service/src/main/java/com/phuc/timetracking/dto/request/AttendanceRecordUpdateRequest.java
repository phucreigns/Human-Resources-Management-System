package com.phuc.timetracking.dto.request;

import com.phuc.timetracking.enums.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRecordUpdateRequest {

    LocalTime checkInTime;

    LocalTime checkOutTime;

    AttendanceStatus status;
}

