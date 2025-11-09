package com.phuc.hr.mapper;

import com.phuc.hr.dto.request.DepartmentCreateRequest;
import com.phuc.hr.dto.request.DepartmentUpdateRequest;
import com.phuc.hr.dto.response.DepartmentResponse;
import com.phuc.hr.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    Department toDepartment(DepartmentCreateRequest request);
    DepartmentResponse toDepartmentResponse(Department department);
    void updateDepartment(@MappingTarget Department department, DepartmentUpdateRequest request);
}


