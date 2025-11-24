package com.phuc.payroll.mapper;

import com.phuc.payroll.dto.request.SalaryStructureCreateRequest;
import com.phuc.payroll.dto.request.SalaryStructureUpdateRequest;
import com.phuc.payroll.dto.response.SalaryStructureResponse;
import com.phuc.payroll.entity.SalaryStructure;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class SalaryStructureMapperImpl implements SalaryStructureMapper {

    @Override
    public SalaryStructure toSalaryStructure(SalaryStructureCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        SalaryStructure.SalaryStructureBuilder salaryStructure = SalaryStructure.builder();

        salaryStructure.baseSalary( request.getBaseSalary() );
        salaryStructure.effectiveFrom( request.getEffectiveFrom() );
        salaryStructure.isActive( request.getIsActive() );
        Map<String, Object> map = request.getSalaryComponents();
        if ( map != null ) {
            salaryStructure.salaryComponents( new LinkedHashMap<String, Object>( map ) );
        }
        salaryStructure.totalSalary( request.getTotalSalary() );
        salaryStructure.userId( request.getUserId() );

        return salaryStructure.build();
    }

    @Override
    public SalaryStructureResponse toSalaryStructureResponse(SalaryStructure salaryStructure) {
        if ( salaryStructure == null ) {
            return null;
        }

        SalaryStructureResponse salaryStructureResponse = new SalaryStructureResponse();

        salaryStructureResponse.setBaseSalary( salaryStructure.getBaseSalary() );
        salaryStructureResponse.setCreatedAt( salaryStructure.getCreatedAt() );
        salaryStructureResponse.setEffectiveFrom( salaryStructure.getEffectiveFrom() );
        salaryStructureResponse.setIsActive( salaryStructure.getIsActive() );
        Map<String, Object> map = salaryStructure.getSalaryComponents();
        if ( map != null ) {
            salaryStructureResponse.setSalaryComponents( new LinkedHashMap<String, Object>( map ) );
        }
        salaryStructureResponse.setSalaryStructureId( salaryStructure.getSalaryStructureId() );
        salaryStructureResponse.setTotalSalary( salaryStructure.getTotalSalary() );
        salaryStructureResponse.setUpdatedAt( salaryStructure.getUpdatedAt() );
        salaryStructureResponse.setUserId( salaryStructure.getUserId() );

        return salaryStructureResponse;
    }

    @Override
    public void updateSalaryStructure(SalaryStructure salaryStructure, SalaryStructureUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        salaryStructure.setBaseSalary( request.getBaseSalary() );
        salaryStructure.setEffectiveFrom( request.getEffectiveFrom() );
        salaryStructure.setIsActive( request.getIsActive() );
        if ( salaryStructure.getSalaryComponents() != null ) {
            Map<String, Object> map = request.getSalaryComponents();
            if ( map != null ) {
                salaryStructure.getSalaryComponents().clear();
                salaryStructure.getSalaryComponents().putAll( map );
            }
            else {
                salaryStructure.setSalaryComponents( null );
            }
        }
        else {
            Map<String, Object> map = request.getSalaryComponents();
            if ( map != null ) {
                salaryStructure.setSalaryComponents( new LinkedHashMap<String, Object>( map ) );
            }
        }
        salaryStructure.setTotalSalary( request.getTotalSalary() );
    }
}
