package com.phuc.timetracking.service.Impl;

import com.phuc.timetracking.dto.request.LeaveRequestCreateRequest;
import com.phuc.timetracking.dto.request.LeaveRequestUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveRequestResponse;
import com.phuc.timetracking.entity.LeaveRequest;
import com.phuc.timetracking.entity.LeaveType;
import com.phuc.timetracking.enums.LeaveRequestStatus;
import com.phuc.timetracking.exception.AppException;
import com.phuc.timetracking.exception.ErrorCode;
import com.phuc.timetracking.mapper.LeaveRequestMapper;
import com.phuc.timetracking.repository.LeaveRequestRepository;
import com.phuc.timetracking.repository.LeaveTypeRepository;
import com.phuc.timetracking.service.LeaveRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestServiceImpl implements LeaveRequestService {

    LeaveRequestRepository leaveRequestRepository;
    LeaveRequestMapper leaveRequestMapper;
    LeaveTypeRepository leaveTypeRepository;

    @Override
    public List<LeaveRequestResponse> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream()
                .map(leaveRequestMapper::toLeaveRequestResponse)
                .toList();
    }

    @Override
    public LeaveRequestResponse getLeaveRequestById(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));
        return leaveRequestMapper.toLeaveRequestResponse(leaveRequest);
    }

    @Override
    public List<LeaveRequestResponse> getLeaveRequestsByUserId(String userId) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByUserId(userId);
        return leaveRequests.stream()
                .map(leaveRequestMapper::toLeaveRequestResponse)
                .toList();
    }

    @Override
    @Transactional
    public LeaveRequestResponse createLeaveRequest(LeaveRequestCreateRequest request) {
        // Validate date range
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new AppException(ErrorCode.INVALID_DATE_RANGE);
        }

        // Validate leave type exists
        LeaveType leaveType = leaveTypeRepository.findByLeaveTypeId(request.getLeaveTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_TYPE_NOT_FOUND));

        LeaveRequest leaveRequest = leaveRequestMapper.toLeaveRequest(request);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStatus(LeaveRequestStatus.PENDING);

        // Calculate total days
        long days = java.time.temporal.ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;
        leaveRequest.setTotalDays((int) days);

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Created leave request with ID: {}", savedLeaveRequest.getLeaveRequestId());
        return leaveRequestMapper.toLeaveRequestResponse(savedLeaveRequest);
    }

    @Override
    @Transactional
    public LeaveRequestResponse updateLeaveRequest(Long leaveRequestId, LeaveRequestUpdateRequest request) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));

        // Validate date range if dates are being updated
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getEndDate().isBefore(request.getStartDate())) {
                throw new AppException(ErrorCode.INVALID_DATE_RANGE);
            }
        } else if (request.getStartDate() != null && leaveRequest.getEndDate() != null) {
            if (leaveRequest.getEndDate().isBefore(request.getStartDate())) {
                throw new AppException(ErrorCode.INVALID_DATE_RANGE);
            }
        } else if (request.getEndDate() != null && leaveRequest.getStartDate() != null) {
            if (request.getEndDate().isBefore(leaveRequest.getStartDate())) {
                throw new AppException(ErrorCode.INVALID_DATE_RANGE);
            }
        }

        // Update leave type if provided
        if (request.getLeaveTypeId() != null) {
            LeaveType leaveType = leaveTypeRepository.findByLeaveTypeId(request.getLeaveTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.LEAVE_TYPE_NOT_FOUND));
            leaveRequest.setLeaveType(leaveType);
        }

        leaveRequestMapper.updateLeaveRequest(leaveRequest, request);

        // Recalculate total days
        if (leaveRequest.getStartDate() != null && leaveRequest.getEndDate() != null) {
            long days = java.time.temporal.ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;
            leaveRequest.setTotalDays((int) days);
        }

        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Updated leave request with ID: {}", leaveRequestId);
        return leaveRequestMapper.toLeaveRequestResponse(updatedLeaveRequest);
    }

    @Override
    @Transactional
    public LeaveRequestResponse approveLeaveRequest(Long leaveRequestId, String approvedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));
        
        leaveRequest.setStatus(LeaveRequestStatus.APPROVED);
        leaveRequest.setApprovedBy(approvedBy);
        
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Approved leave request with ID: {} by {}", leaveRequestId, approvedBy);
        return leaveRequestMapper.toLeaveRequestResponse(savedLeaveRequest);
    }

    @Override
    @Transactional
    public LeaveRequestResponse rejectLeaveRequest(Long leaveRequestId, String approvedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));
        
        leaveRequest.setStatus(LeaveRequestStatus.REJECTED);
        leaveRequest.setApprovedBy(approvedBy);
        
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Rejected leave request with ID: {} by {}", leaveRequestId, approvedBy);
        return leaveRequestMapper.toLeaveRequestResponse(savedLeaveRequest);
    }

    @Override
    @Transactional
    public void deleteLeaveRequest(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));
        leaveRequestRepository.delete(leaveRequest);
        log.info("Deleted leave request with ID: {}", leaveRequestId);
    }
}

