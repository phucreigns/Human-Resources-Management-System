package com.phuc.timetracking.mapper;

import com.phuc.timetracking.dto.request.AttendanceRecordCreateRequest;
import com.phuc.timetracking.dto.request.AttendanceRecordUpdateRequest;
import com.phuc.timetracking.dto.response.AttendanceRecordResponse;
import com.phuc.timetracking.entity.AttendanceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceRecordMapper {
    AttendanceRecord toAttendanceRecord(AttendanceRecordCreateRequest request);
    AttendanceRecordResponse toAttendanceRecordResponse(AttendanceRecord attendanceRecord);
    void updateAttendanceRecord(@MappingTarget AttendanceRecord attendanceRecord, AttendanceRecordUpdateRequest request);
}

