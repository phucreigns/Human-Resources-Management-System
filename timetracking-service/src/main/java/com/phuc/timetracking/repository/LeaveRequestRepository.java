package com.phuc.timetracking.repository;

import com.phuc.timetracking.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    Optional<LeaveRequest> findByLeaveRequestId(Long leaveRequestId);
    List<LeaveRequest> findByUserId(String userId);
    List<LeaveRequest> findByStatus(com.phuc.timetracking.enums.LeaveRequestStatus status);
}

