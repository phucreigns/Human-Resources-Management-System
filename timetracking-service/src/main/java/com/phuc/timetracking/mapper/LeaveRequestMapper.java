package com.phuc.timetracking.mapper;

import com.phuc.timetracking.dto.request.LeaveRequestCreateRequest;
import com.phuc.timetracking.dto.request.LeaveRequestUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveRequestResponse;
import com.phuc.timetracking.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = LeaveTypeMapper.class)
public interface LeaveRequestMapper {
    LeaveRequest toLeaveRequest(LeaveRequestCreateRequest request);
    LeaveRequestResponse toLeaveRequestResponse(LeaveRequest leaveRequest);
    void updateLeaveRequest(@MappingTarget LeaveRequest leaveRequest, LeaveRequestUpdateRequest request);
}

