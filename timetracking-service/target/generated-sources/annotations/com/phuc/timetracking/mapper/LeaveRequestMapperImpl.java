package com.phuc.timetracking.mapper;

import com.phuc.timetracking.dto.request.LeaveRequestCreateRequest;
import com.phuc.timetracking.dto.request.LeaveRequestUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveRequestResponse;
import com.phuc.timetracking.entity.LeaveRequest;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class LeaveRequestMapperImpl implements LeaveRequestMapper {

    @Autowired
    private LeaveTypeMapper leaveTypeMapper;

    @Override
    public LeaveRequest toLeaveRequest(LeaveRequestCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        LeaveRequest.LeaveRequestBuilder leaveRequest = LeaveRequest.builder();

        leaveRequest.endDate( request.getEndDate() );
        leaveRequest.startDate( request.getStartDate() );
        leaveRequest.userId( request.getUserId() );

        return leaveRequest.build();
    }

    @Override
    public LeaveRequestResponse toLeaveRequestResponse(LeaveRequest leaveRequest) {
        if ( leaveRequest == null ) {
            return null;
        }

        LeaveRequestResponse.LeaveRequestResponseBuilder leaveRequestResponse = LeaveRequestResponse.builder();

        leaveRequestResponse.approvedBy( leaveRequest.getApprovedBy() );
        leaveRequestResponse.createdAt( leaveRequest.getCreatedAt() );
        leaveRequestResponse.endDate( leaveRequest.getEndDate() );
        leaveRequestResponse.leaveRequestId( leaveRequest.getLeaveRequestId() );
        leaveRequestResponse.leaveType( leaveTypeMapper.toLeaveTypeResponse( leaveRequest.getLeaveType() ) );
        leaveRequestResponse.startDate( leaveRequest.getStartDate() );
        leaveRequestResponse.status( leaveRequest.getStatus() );
        leaveRequestResponse.totalDays( leaveRequest.getTotalDays() );
        leaveRequestResponse.updatedAt( leaveRequest.getUpdatedAt() );
        leaveRequestResponse.userId( leaveRequest.getUserId() );

        return leaveRequestResponse.build();
    }

    @Override
    public void updateLeaveRequest(LeaveRequest leaveRequest, LeaveRequestUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        leaveRequest.setApprovedBy( request.getApprovedBy() );
        leaveRequest.setEndDate( request.getEndDate() );
        leaveRequest.setStartDate( request.getStartDate() );
        leaveRequest.setStatus( request.getStatus() );
    }
}
