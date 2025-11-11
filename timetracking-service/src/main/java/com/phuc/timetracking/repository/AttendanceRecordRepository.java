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
    List<AttendanceRecord> findByEmployeeId(String employeeId);
    List<AttendanceRecord> findByEmployeeIdAndAttendanceDateBetween(String employeeId, LocalDate startDate, LocalDate endDate);
    Optional<AttendanceRecord> findByEmployeeIdAndAttendanceDate(String employeeId, LocalDate attendanceDate);
}

