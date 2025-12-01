package com.phuc.timetracking.controller;

import com.phuc.timetracking.dto.ApiResponse;
import com.phuc.timetracking.dto.request.LeaveTypeCreateRequest;
import com.phuc.timetracking.dto.request.LeaveTypeUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveTypeResponse;
import com.phuc.timetracking.service.LeaveTypeService;
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
@RequestMapping("/leave-types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveTypeController {

    LeaveTypeService leaveTypeService;

    @GetMapping
    public ApiResponse<List<LeaveTypeResponse>> getAllLeaveTypes() {
        return ApiResponse.<List<LeaveTypeResponse>>builder()
                .result(leaveTypeService.getAllLeaveTypes())
                .message("Leave types retrieved successfully")
                .build();
    }

    @GetMapping("/{leaveTypeId}")
    public ApiResponse<LeaveTypeResponse> getLeaveTypeById(@PathVariable Long leaveTypeId) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .result(leaveTypeService.getLeaveTypeById(leaveTypeId))
                .message("Leave type retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LeaveTypeResponse> createLeaveType(@RequestBody @Valid LeaveTypeCreateRequest request) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .result(leaveTypeService.createLeaveType(request))
                .message("Leave type created successfully")
                .build();
    }

    @PutMapping("/{leaveTypeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LeaveTypeResponse> updateLeaveType(
            @PathVariable Long leaveTypeId,
            @RequestBody @Valid LeaveTypeUpdateRequest request) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .result(leaveTypeService.updateLeaveType(leaveTypeId, request))
                .message("Leave type updated successfully")
                .build();
    }

    @DeleteMapping("/{leaveTypeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long leaveTypeId) {
        leaveTypeService.deleteLeaveType(leaveTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

