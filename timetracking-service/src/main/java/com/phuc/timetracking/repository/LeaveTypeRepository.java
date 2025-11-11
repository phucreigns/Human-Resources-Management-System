package com.phuc.timetracking.repository;

import com.phuc.timetracking.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByLeaveTypeId(Long leaveTypeId);
    Optional<LeaveType> findByLeaveTypeCode(String leaveTypeCode);
    boolean existsByLeaveTypeCode(String leaveTypeCode);
}

