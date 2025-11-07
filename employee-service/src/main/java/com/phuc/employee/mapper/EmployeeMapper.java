package com.phuc.employee.mapper;

import com.phuc.employee.dto.request.EmployeeCreateRequest;
import com.phuc.employee.dto.request.EmployeeUpdateRequest;
import com.phuc.employee.dto.response.EmployeeResponse;
import com.phuc.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeCreateRequest request);
    EmployeeResponse toEmployeeResponse(Employee employee);
    void updateEmployee(@MappingTarget Employee employee, EmployeeUpdateRequest request);
}


