package com.phuc.timetracking.repository;

import com.phuc.timetracking.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findByAttendanceId(Long attendanceId);
    List<AttendanceRecord> findByUserId(String userId);
    List<AttendanceRecord> findByUserIdAndAttendanceDateBetween(String userId, LocalDate startDate, LocalDate endDate);
    Optional<AttendanceRecord> findByUserIdAndAttendanceDate(String userId, LocalDate attendanceDate);
}

