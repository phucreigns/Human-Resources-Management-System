package com.phuc.timetracking.service.Impl;

import com.phuc.timetracking.dto.request.AttendanceRecordCreateRequest;
import com.phuc.timetracking.dto.request.AttendanceRecordUpdateRequest;
import com.phuc.timetracking.dto.response.AttendanceRecordResponse;
import com.phuc.timetracking.entity.AttendanceRecord;
import com.phuc.timetracking.exception.AppException;
import com.phuc.timetracking.exception.ErrorCode;
import com.phuc.timetracking.mapper.AttendanceRecordMapper;
import com.phuc.timetracking.repository.AttendanceRecordRepository;
import com.phuc.timetracking.service.AttendanceRecordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

    AttendanceRecordRepository attendanceRecordRepository;
    AttendanceRecordMapper attendanceRecordMapper;

    @Override
    public List<AttendanceRecordResponse> getAllAttendanceRecords() {
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findAll();
        return attendanceRecords.stream()
                .map(attendanceRecordMapper::toAttendanceRecordResponse)
                .toList();
    }

    @Override
    public AttendanceRecordResponse getAttendanceRecordById(Long attendanceId) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findByAttendanceId(attendanceId)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_RECORD_NOT_FOUND));
        return attendanceRecordMapper.toAttendanceRecordResponse(attendanceRecord);
    }

    @Override
    public List<AttendanceRecordResponse> getAttendanceRecordsByUserId(String userId) {
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByUserId(userId);
        return attendanceRecords.stream()
                .map(attendanceRecordMapper::toAttendanceRecordResponse)
                .toList();
    }

    @Override
    public List<AttendanceRecordResponse> getAttendanceRecordsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByUserIdAndAttendanceDateBetween(userId, startDate, endDate);
        return attendanceRecords.stream()
                .map(attendanceRecordMapper::toAttendanceRecordResponse)
                .toList();
    }

    @Override
    @Transactional
    public AttendanceRecordResponse createAttendanceRecord(AttendanceRecordCreateRequest request) {
        // Validate check-out time is after check-in time
        if (request.getCheckInTime() != null && request.getCheckOutTime() != null) {
            if (!request.getCheckOutTime().isAfter(request.getCheckInTime())) {
                throw new AppException(ErrorCode.INVALID_CHECK_OUT_TIME);
            }
        }

        AttendanceRecord attendanceRecord = attendanceRecordMapper.toAttendanceRecord(request);
        
        // Calculate hours worked
        if (attendanceRecord.getCheckInTime() != null && attendanceRecord.getCheckOutTime() != null) {
            long minutes = java.time.Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime()).toMinutes();
            attendanceRecord.setHoursWorked(minutes / 60.0);
        }

        AttendanceRecord savedAttendanceRecord = attendanceRecordRepository.save(attendanceRecord);
        log.info("Created attendance record with ID: {}", savedAttendanceRecord.getAttendanceId());
        return attendanceRecordMapper.toAttendanceRecordResponse(savedAttendanceRecord);
    }

    @Override
    @Transactional
    public AttendanceRecordResponse updateAttendanceRecord(Long attendanceId, AttendanceRecordUpdateRequest request) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findByAttendanceId(attendanceId)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_RECORD_NOT_FOUND));

        // Validate check-out time is after check-in time
        LocalTime checkInTime = request.getCheckInTime() != null ? request.getCheckInTime() : attendanceRecord.getCheckInTime();
        LocalTime checkOutTime = request.getCheckOutTime() != null ? request.getCheckOutTime() : attendanceRecord.getCheckOutTime();
        
        if (checkInTime != null && checkOutTime != null && !checkOutTime.isAfter(checkInTime)) {
            throw new AppException(ErrorCode.INVALID_CHECK_OUT_TIME);
        }

        attendanceRecordMapper.updateAttendanceRecord(attendanceRecord, request);
        
        // Recalculate hours worked
        if (attendanceRecord.getCheckInTime() != null && attendanceRecord.getCheckOutTime() != null) {
            long minutes = java.time.Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime()).toMinutes();
            attendanceRecord.setHoursWorked(minutes / 60.0);
        }

        AttendanceRecord updatedAttendanceRecord = attendanceRecordRepository.save(attendanceRecord);
        log.info("Updated attendance record with ID: {}", attendanceId);
        return attendanceRecordMapper.toAttendanceRecordResponse(updatedAttendanceRecord);
    }

    @Override
    @Transactional
    public void deleteAttendanceRecord(Long attendanceId) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findByAttendanceId(attendanceId)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_RECORD_NOT_FOUND));
        attendanceRecordRepository.delete(attendanceRecord);
        log.info("Deleted attendance record with ID: {}", attendanceId);
    }
}

