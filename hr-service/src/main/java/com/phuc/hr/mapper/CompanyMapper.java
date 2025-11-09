package com.phuc.hr.mapper;

import com.phuc.hr.dto.request.CompanyCreateRequest;
import com.phuc.hr.dto.request.CompanyUpdateRequest;
import com.phuc.hr.dto.response.CompanyResponse;
import com.phuc.hr.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
    Company toCompany(CompanyCreateRequest request);
    CompanyResponse toCompanyResponse(Company company);
    void updateCompany(@MappingTarget Company company, CompanyUpdateRequest request);
}


