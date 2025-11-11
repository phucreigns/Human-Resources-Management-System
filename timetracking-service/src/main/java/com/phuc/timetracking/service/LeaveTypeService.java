package com.phuc.timetracking.service;

import com.phuc.timetracking.dto.request.LeaveTypeCreateRequest;
import com.phuc.timetracking.dto.request.LeaveTypeUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveTypeResponse;

import java.util.List;

public interface LeaveTypeService {
    List<LeaveTypeResponse> getAllLeaveTypes();
    LeaveTypeResponse getLeaveTypeById(Long leaveTypeId);
    LeaveTypeResponse createLeaveType(LeaveTypeCreateRequest request);
    LeaveTypeResponse updateLeaveType(Long leaveTypeId, LeaveTypeUpdateRequest request);
    void deleteLeaveType(Long leaveTypeId);
}

