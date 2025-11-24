package com.phuc.hr.mapper;

import com.phuc.hr.dto.request.CompanyCreateRequest;
import com.phuc.hr.dto.request.CompanyUpdateRequest;
import com.phuc.hr.dto.response.CompanyResponse;
import com.phuc.hr.entity.Company;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public Company toCompany(CompanyCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Company company = new Company();

        company.setName( request.getName() );
        company.setAddress( request.getAddress() );
        company.setPhone( request.getPhone() );
        company.setEmail( request.getEmail() );
        company.setTaxCode( request.getTaxCode() );
        company.setStatus( request.getStatus() );

        return company;
    }

    @Override
    public CompanyResponse toCompanyResponse(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setCompanyId( company.getCompanyId() );
        companyResponse.setName( company.getName() );
        companyResponse.setAddress( company.getAddress() );
        companyResponse.setPhone( company.getPhone() );
        companyResponse.setEmail( company.getEmail() );
        companyResponse.setTaxCode( company.getTaxCode() );
        companyResponse.setStatus( company.getStatus() );
        companyResponse.setCreatedAt( company.getCreatedAt() );
        companyResponse.setUpdatedAt( company.getUpdatedAt() );

        return companyResponse;
    }

    @Override
    public void updateCompany(Company company, CompanyUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        company.setName( request.getName() );
        company.setAddress( request.getAddress() );
        company.setPhone( request.getPhone() );
        company.setEmail( request.getEmail() );
        company.setTaxCode( request.getTaxCode() );
        company.setStatus( request.getStatus() );
    }
}
