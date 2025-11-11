package com.phuc.timetracking.service.Impl;

import com.phuc.timetracking.dto.request.LeaveTypeCreateRequest;
import com.phuc.timetracking.dto.request.LeaveTypeUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveTypeResponse;
import com.phuc.timetracking.entity.LeaveType;
import com.phuc.timetracking.exception.AppException;
import com.phuc.timetracking.exception.ErrorCode;
import com.phuc.timetracking.mapper.LeaveTypeMapper;
import com.phuc.timetracking.repository.LeaveTypeRepository;
import com.phuc.timetracking.service.LeaveTypeService;
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
public class LeaveTypeServiceImpl implements LeaveTypeService {

    LeaveTypeRepository leaveTypeRepository;
    LeaveTypeMapper leaveTypeMapper;

    @Override
    public List<LeaveTypeResponse> getAllLeaveTypes() {
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        return leaveTypes.stream()
                .map(leaveTypeMapper::toLeaveTypeResponse)
                .toList();
    }

    @Override
    public LeaveTypeResponse getLeaveTypeById(Long leaveTypeId) {
        LeaveType leaveType = leaveTypeRepository.findByLeaveTypeId(leaveTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_TYPE_NOT_FOUND));
        return leaveTypeMapper.toLeaveTypeResponse(leaveType);
    }

    @Override
    @Transactional
    public LeaveTypeResponse createLeaveType(LeaveTypeCreateRequest request) {
        // Check if leave type code already exists
        if (leaveTypeRepository.existsByLeaveTypeCode(request.getLeaveTypeCode())) {
            throw new AppException(ErrorCode.LEAVE_TYPE_CODE_EXISTED);
        }

        LeaveType leaveType = leaveTypeMapper.toLeaveType(request);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        log.info("Created leave type with ID: {}", savedLeaveType.getLeaveTypeId());
        return leaveTypeMapper.toLeaveTypeResponse(savedLeaveType);
    }

    @Override
    @Transactional
    public LeaveTypeResponse updateLeaveType(Long leaveTypeId, LeaveTypeUpdateRequest request) {
        LeaveType leaveType = leaveTypeRepository.findByLeaveTypeId(leaveTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_TYPE_NOT_FOUND));

        // Check if leave type code is being updated and if it already exists
        if (request.getLeaveTypeCode() != null && !request.getLeaveTypeCode().isEmpty()) {
            leaveTypeRepository.findByLeaveTypeCode(request.getLeaveTypeCode())
                    .ifPresent(existingLeaveType -> {
                        if (!existingLeaveType.getLeaveTypeId().equals(leaveTypeId)) {
                            throw new AppException(ErrorCode.LEAVE_TYPE_CODE_EXISTED);
                        }
                    });
        }

        leaveTypeMapper.updateLeaveType(leaveType, request);
        LeaveType updatedLeaveType = leaveTypeRepository.save(leaveType);
        log.info("Updated leave type with ID: {}", leaveTypeId);
        return leaveTypeMapper.toLeaveTypeResponse(updatedLeaveType);
    }

    @Override
    @Transactional
    public void deleteLeaveType(Long leaveTypeId) {
        LeaveType leaveType = leaveTypeRepository.findByLeaveTypeId(leaveTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_TYPE_NOT_FOUND));
        leaveTypeRepository.delete(leaveType);
        log.info("Deleted leave type with ID: {}", leaveTypeId);
    }
}

