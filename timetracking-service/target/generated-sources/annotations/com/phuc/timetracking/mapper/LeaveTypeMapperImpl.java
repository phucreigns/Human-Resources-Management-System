package com.phuc.timetracking.mapper;

import com.phuc.timetracking.dto.request.LeaveTypeCreateRequest;
import com.phuc.timetracking.dto.request.LeaveTypeUpdateRequest;
import com.phuc.timetracking.dto.response.LeaveTypeResponse;
import com.phuc.timetracking.entity.LeaveType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class LeaveTypeMapperImpl implements LeaveTypeMapper {

    @Override
    public LeaveType toLeaveType(LeaveTypeCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        LeaveType.LeaveTypeBuilder leaveType = LeaveType.builder();

        leaveType.isPaid( request.getIsPaid() );
        leaveType.leaveTypeCode( request.getLeaveTypeCode() );
        leaveType.leaveTypeName( request.getLeaveTypeName() );
        leaveType.maxDaysPerYear( request.getMaxDaysPerYear() );
        leaveType.requiresApproval( request.getRequiresApproval() );

        return leaveType.build();
    }

    @Override
    public LeaveTypeResponse toLeaveTypeResponse(LeaveType leaveType) {
        if ( leaveType == null ) {
            return null;
        }

        LeaveTypeResponse.LeaveTypeResponseBuilder leaveTypeResponse = LeaveTypeResponse.builder();

        leaveTypeResponse.createdAt( leaveType.getCreatedAt() );
        leaveTypeResponse.isPaid( leaveType.getIsPaid() );
        leaveTypeResponse.leaveTypeCode( leaveType.getLeaveTypeCode() );
        leaveTypeResponse.leaveTypeId( leaveType.getLeaveTypeId() );
        leaveTypeResponse.leaveTypeName( leaveType.getLeaveTypeName() );
        leaveTypeResponse.maxDaysPerYear( leaveType.getMaxDaysPerYear() );
        leaveTypeResponse.requiresApproval( leaveType.getRequiresApproval() );
        leaveTypeResponse.updatedAt( leaveType.getUpdatedAt() );

        return leaveTypeResponse.build();
    }

    @Override
    public void updateLeaveType(LeaveType leaveType, LeaveTypeUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        leaveType.setIsPaid( request.getIsPaid() );
        leaveType.setLeaveTypeCode( request.getLeaveTypeCode() );
        leaveType.setLeaveTypeName( request.getLeaveTypeName() );
        leaveType.setMaxDaysPerYear( request.getMaxDaysPerYear() );
        leaveType.setRequiresApproval( request.getRequiresApproval() );
    }
}
