package com.phuc.timetracking.entity;

import com.phuc.timetracking.enums.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    Long attendanceId;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "attendance_date", nullable = false)
    LocalDate attendanceDate;

    @Column(name = "check_in_time")
    LocalTime checkInTime;

    @Column(name = "check_out_time")
    LocalTime checkOutTime;

    @Column(name = "hours_worked")
    Double hoursWorked;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    AttendanceStatus status;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateHoursWorked();
    }

    private void calculateHoursWorked() {
        if (checkInTime != null && checkOutTime != null && checkOutTime.isAfter(checkInTime)) {
            long minutes = java.time.Duration.between(checkInTime, checkOutTime).toMinutes();
            hoursWorked = minutes / 60.0;
        }
    }
}

