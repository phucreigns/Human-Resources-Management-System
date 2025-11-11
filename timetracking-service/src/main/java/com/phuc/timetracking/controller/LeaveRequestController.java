package com.phuc.timetracking.controller;

import com.phuc.timetracking.dto.ApiResponse;
import com.phuc.timetracking.dto.request.LeaveRequestCreateRequest;
import com.phuc.timetracking.dto.request.LeaveRequestUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveRequestResponse;
import com.phuc.timetracking.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestController {

    LeaveRequestService leaveRequestService;

    @GetMapping
    public ApiResponse<List<LeaveRequestResponse>> getAllLeaveRequests() {
        return ApiResponse.<List<LeaveRequestResponse>>builder()
                .result(leaveRequestService.getAllLeaveRequests())
                .message("Leave requests retrieved successfully")
                .build();
    }

    @GetMapping("/{leaveRequestId}")
    public ApiResponse<LeaveRequestResponse> getLeaveRequestById(@PathVariable Long leaveRequestId) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .result(leaveRequestService.getLeaveRequestById(leaveRequestId))
                .message("Leave request retrieved successfully")
                .build();
    }

    @GetMapping("/employee/{employeeId}")
    public ApiResponse<List<LeaveRequestResponse>> getLeaveRequestsByEmployeeId(@PathVariable String employeeId) {
        return ApiResponse.<List<LeaveRequestResponse>>builder()
                .result(leaveRequestService.getLeaveRequestsByEmployeeId(employeeId))
                .message("Leave requests retrieved successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<LeaveRequestResponse> createLeaveRequest(@RequestBody @Valid LeaveRequestCreateRequest request) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .result(leaveRequestService.createLeaveRequest(request))
                .message("Leave request created successfully")
                .build();
    }

    @PutMapping("/{leaveRequestId}")
    public ApiResponse<LeaveRequestResponse> updateLeaveRequest(
            @PathVariable Long leaveRequestId,
            @RequestBody @Valid LeaveRequestUpdateRequest request) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .result(leaveRequestService.updateLeaveRequest(leaveRequestId, request))
                .message("Leave request updated successfully")
                .build();
    }

    @PutMapping("/{leaveRequestId}/approve")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ApiResponse<LeaveRequestResponse> approveLeaveRequest(
            @PathVariable Long leaveRequestId,
            @RequestParam String approvedBy) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .result(leaveRequestService.approveLeaveRequest(leaveRequestId, approvedBy))
                .message("Leave request approved successfully")
                .build();
    }

    @PutMapping("/{leaveRequestId}/reject")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ApiResponse<LeaveRequestResponse> rejectLeaveRequest(
            @PathVariable Long leaveRequestId,
            @RequestParam String approvedBy) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .result(leaveRequestService.rejectLeaveRequest(leaveRequestId, approvedBy))
                .message("Leave request rejected successfully")
                .build();
    }

    @DeleteMapping("/{leaveRequestId}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long leaveRequestId) {
        leaveRequestService.deleteLeaveRequest(leaveRequestId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

