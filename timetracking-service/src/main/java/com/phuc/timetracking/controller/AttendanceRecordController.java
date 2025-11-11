package com.phuc.timetracking.controller;

import com.phuc.timetracking.dto.ApiResponse;
import com.phuc.timetracking.dto.request.AttendanceRecordCreateRequest;
import com.phuc.timetracking.dto.request.AttendanceRecordUpdateRequest;
import com.phuc.timetracking.dto.response.AttendanceRecordResponse;
import com.phuc.timetracking.service.AttendanceRecordService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance-records")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceRecordController {

    AttendanceRecordService attendanceRecordService;

    @GetMapping
    public ApiResponse<List<AttendanceRecordResponse>> getAllAttendanceRecords() {
        return ApiResponse.<List<AttendanceRecordResponse>>builder()
                .result(attendanceRecordService.getAllAttendanceRecords())
                .message("Attendance records retrieved successfully")
                .build();
    }

    @GetMapping("/{attendanceId}")
    public ApiResponse<AttendanceRecordResponse> getAttendanceRecordById(@PathVariable Long attendanceId) {
        return ApiResponse.<AttendanceRecordResponse>builder()
                .result(attendanceRecordService.getAttendanceRecordById(attendanceId))
                .message("Attendance record retrieved successfully")
                .build();
    }

    @GetMapping("/employee/{employeeId}")
    public ApiResponse<List<AttendanceRecordResponse>> getAttendanceRecordsByEmployeeId(@PathVariable String employeeId) {
        return ApiResponse.<List<AttendanceRecordResponse>>builder()
                .result(attendanceRecordService.getAttendanceRecordsByEmployeeId(employeeId))
                .message("Attendance records retrieved successfully")
                .build();
    }

    @GetMapping("/employee/{employeeId}/date-range")
    public ApiResponse<List<AttendanceRecordResponse>> getAttendanceRecordsByDateRange(
            @PathVariable String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ApiResponse.<List<AttendanceRecordResponse>>builder()
                .result(attendanceRecordService.getAttendanceRecordsByEmployeeIdAndDateRange(employeeId, startDate, endDate))
                .message("Attendance records retrieved successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<AttendanceRecordResponse> createAttendanceRecord(@RequestBody @Valid AttendanceRecordCreateRequest request) {
        return ApiResponse.<AttendanceRecordResponse>builder()
                .result(attendanceRecordService.createAttendanceRecord(request))
                .message("Attendance record created successfully")
                .build();
    }

    @PutMapping("/{attendanceId}")
    public ApiResponse<AttendanceRecordResponse> updateAttendanceRecord(
            @PathVariable Long attendanceId,
            @RequestBody @Valid AttendanceRecordUpdateRequest request) {
        return ApiResponse.<AttendanceRecordResponse>builder()
                .result(attendanceRecordService.updateAttendanceRecord(attendanceId, request))
                .message("Attendance record updated successfully")
                .build();
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable Long attendanceId) {
        attendanceRecordService.deleteAttendanceRecord(attendanceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

