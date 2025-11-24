package com.phuc.hr.mapper;

import com.phuc.hr.dto.request.DepartmentCreateRequest;
import com.phuc.hr.dto.request.DepartmentUpdateRequest;
import com.phuc.hr.dto.response.DepartmentResponse;
import com.phuc.hr.entity.Department;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public Department toDepartment(DepartmentCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Department.DepartmentBuilder department = Department.builder();

        department.companyId( request.getCompanyId() );
        department.description( request.getDescription() );
        department.managerId( request.getManagerId() );
        department.name( request.getName() );
        department.status( request.getStatus() );

        return department.build();
    }

    @Override
    public DepartmentResponse toDepartmentResponse(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentResponse departmentResponse = new DepartmentResponse();

        departmentResponse.setCompanyId( department.getCompanyId() );
        departmentResponse.setCreatedAt( department.getCreatedAt() );
        departmentResponse.setDepartmentId( department.getDepartmentId() );
        departmentResponse.setDescription( department.getDescription() );
        departmentResponse.setManagerId( department.getManagerId() );
        departmentResponse.setName( department.getName() );
        departmentResponse.setStatus( department.getStatus() );
        departmentResponse.setUpdatedAt( department.getUpdatedAt() );

        return departmentResponse;
    }

    @Override
    public void updateDepartment(Department department, DepartmentUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        department.setDescription( request.getDescription() );
        department.setManagerId( request.getManagerId() );
        department.setName( request.getName() );
        department.setStatus( request.getStatus() );
    }
}
