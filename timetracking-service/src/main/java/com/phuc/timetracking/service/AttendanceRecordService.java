package com.phuc.timetracking.service;

import com.phuc.timetracking.dto.request.AttendanceRecordCreateRequest;
import com.phuc.timetracking.dto.request.AttendanceRecordUpdateRequest;
import com.phuc.timetracking.dto.response.AttendanceRecordResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordService {
    List<AttendanceRecordResponse> getAllAttendanceRecords();
    AttendanceRecordResponse getAttendanceRecordById(Long attendanceId);
    List<AttendanceRecordResponse> getAttendanceRecordsByUserId(String userId);
    List<AttendanceRecordResponse> getAttendanceRecordsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);
    AttendanceRecordResponse createAttendanceRecord(AttendanceRecordCreateRequest request);
    AttendanceRecordResponse updateAttendanceRecord(Long attendanceId, AttendanceRecordUpdateRequest request);
    void deleteAttendanceRecord(Long attendanceId);
}

