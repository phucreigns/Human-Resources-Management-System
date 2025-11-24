package com.phuc.payroll.mapper;

import com.phuc.payroll.dto.request.SalaryStructureCreateRequest;
import com.phuc.payroll.dto.request.SalaryStructureUpdateRequest;
import com.phuc.payroll.dto.response.SalaryStructureResponse;
import com.phuc.payroll.entity.SalaryStructure;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalaryStructureMapper {
    SalaryStructure toSalaryStructure(SalaryStructureCreateRequest request);
    SalaryStructureResponse toSalaryStructureResponse(SalaryStructure salaryStructure);
    void updateSalaryStructure(@MappingTarget SalaryStructure salaryStructure, SalaryStructureUpdateRequest request);
}





