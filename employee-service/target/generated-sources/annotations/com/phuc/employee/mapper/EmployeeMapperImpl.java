package com.phuc.employee.mapper;

import com.phuc.employee.dto.request.EmployeeCreateRequest;
import com.phuc.employee.dto.request.EmployeeUpdateRequest;
import com.phuc.employee.dto.response.EmployeeResponse;
import com.phuc.employee.entity.Employee;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEmployee(EmployeeCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.companyId( request.getCompanyId() );
        employee.departmentId( request.getDepartmentId() );
        employee.email( request.getEmail() );
        employee.employeeCode( request.getEmployeeCode() );
        employee.fullName( request.getFullName() );
        employee.hireDate( request.getHireDate() );
        employee.managerId( request.getManagerId() );
        employee.phone( request.getPhone() );
        employee.positionId( request.getPositionId() );
        employee.status( request.getStatus() );

        return employee.build();
    }

    @Override
    public EmployeeResponse toEmployeeResponse(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setAuth0Id( employee.getAuth0Id() );
        employeeResponse.setCompanyId( employee.getCompanyId() );
        employeeResponse.setCreatedAt( employee.getCreatedAt() );
        employeeResponse.setDepartmentId( employee.getDepartmentId() );
        employeeResponse.setEmail( employee.getEmail() );
        employeeResponse.setEmployeeCode( employee.getEmployeeCode() );
        employeeResponse.setEmployeeId( employee.getEmployeeId() );
        employeeResponse.setFullName( employee.getFullName() );
        employeeResponse.setHireDate( employee.getHireDate() );
        employeeResponse.setManagerId( employee.getManagerId() );
        employeeResponse.setPhone( employee.getPhone() );
        employeeResponse.setPositionId( employee.getPositionId() );
        employeeResponse.setStatus( employee.getStatus() );
        employeeResponse.setUpdatedAt( employee.getUpdatedAt() );

        return employeeResponse;
    }

    @Override
    public void updateEmployee(Employee employee, EmployeeUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        employee.setEmail( request.getEmail() );
        employee.setFullName( request.getFullName() );
        employee.setHireDate( request.getHireDate() );
        employee.setPhone( request.getPhone() );
    }
}
