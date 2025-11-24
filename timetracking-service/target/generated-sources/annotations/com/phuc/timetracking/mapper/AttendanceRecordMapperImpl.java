package com.phuc.timetracking.mapper;

import com.phuc.timetracking.dto.request.AttendanceRecordCreateRequest;
import com.phuc.timetracking.dto.request.AttendanceRecordUpdateRequest;
import com.phuc.timetracking.dto.response.AttendanceRecordResponse;
import com.phuc.timetracking.entity.AttendanceRecord;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class AttendanceRecordMapperImpl implements AttendanceRecordMapper {

    @Override
    public AttendanceRecord toAttendanceRecord(AttendanceRecordCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        AttendanceRecord.AttendanceRecordBuilder attendanceRecord = AttendanceRecord.builder();

        attendanceRecord.attendanceDate( request.getAttendanceDate() );
        attendanceRecord.checkInTime( request.getCheckInTime() );
        attendanceRecord.checkOutTime( request.getCheckOutTime() );
        attendanceRecord.status( request.getStatus() );
        attendanceRecord.userId( request.getUserId() );

        return attendanceRecord.build();
    }

    @Override
    public AttendanceRecordResponse toAttendanceRecordResponse(AttendanceRecord attendanceRecord) {
        if ( attendanceRecord == null ) {
            return null;
        }

        AttendanceRecordResponse.AttendanceRecordResponseBuilder attendanceRecordResponse = AttendanceRecordResponse.builder();

        attendanceRecordResponse.attendanceDate( attendanceRecord.getAttendanceDate() );
        attendanceRecordResponse.attendanceId( attendanceRecord.getAttendanceId() );
        attendanceRecordResponse.checkInTime( attendanceRecord.getCheckInTime() );
        attendanceRecordResponse.checkOutTime( attendanceRecord.getCheckOutTime() );
        attendanceRecordResponse.createdAt( attendanceRecord.getCreatedAt() );
        attendanceRecordResponse.hoursWorked( attendanceRecord.getHoursWorked() );
        attendanceRecordResponse.status( attendanceRecord.getStatus() );
        attendanceRecordResponse.updatedAt( attendanceRecord.getUpdatedAt() );
        attendanceRecordResponse.userId( attendanceRecord.getUserId() );

        return attendanceRecordResponse.build();
    }

    @Override
    public void updateAttendanceRecord(AttendanceRecord attendanceRecord, AttendanceRecordUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        attendanceRecord.setCheckInTime( request.getCheckInTime() );
        attendanceRecord.setCheckOutTime( request.getCheckOutTime() );
        attendanceRecord.setStatus( request.getStatus() );
    }
}
