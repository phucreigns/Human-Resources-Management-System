package com.phuc.timetracking.mapper;

import com.phuc.timetracking.dto.request.LeaveTypeCreateRequest;
import com.phuc.timetracking.dto.request.LeaveTypeUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveTypeResponse;
import com.phuc.timetracking.entity.LeaveType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveTypeMapper {
    LeaveType toLeaveType(LeaveTypeCreateRequest request);
    LeaveTypeResponse toLeaveTypeResponse(LeaveType leaveType);
    void updateLeaveType(@MappingTarget LeaveType leaveType, LeaveTypeUpdateRequest request);
}

