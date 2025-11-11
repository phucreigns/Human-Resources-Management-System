package com.phuc.timetracking.service;

import com.phuc.timetracking.dto.request.LeaveRequestCreateRequest;
import com.phuc.timetracking.dto.request.LeaveRequestUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveRequestResponse;

import java.util.List;

public interface LeaveRequestService {
    List<LeaveRequestResponse> getAllLeaveRequests();
    LeaveRequestResponse getLeaveRequestById(Long leaveRequestId);
    List<LeaveRequestResponse> getLeaveRequestsByEmployeeId(String employeeId);
    LeaveRequestResponse createLeaveRequest(LeaveRequestCreateRequest request);
    LeaveRequestResponse updateLeaveRequest(Long leaveRequestId, LeaveRequestUpdateRequest request);
    LeaveRequestResponse approveLeaveRequest(Long leaveRequestId, String approvedBy);
    LeaveRequestResponse rejectLeaveRequest(Long leaveRequestId, String approvedBy);
    void deleteLeaveRequest(Long leaveRequestId);
}

