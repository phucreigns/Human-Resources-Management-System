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

        Company.CompanyBuilder company = Company.builder();

        company.address( request.getAddress() );
        company.email( request.getEmail() );
        company.name( request.getName() );
        company.phone( request.getPhone() );
        company.status( request.getStatus() );
        company.taxCode( request.getTaxCode() );

        return company.build();
    }

    @Override
    public CompanyResponse toCompanyResponse(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setAddress( company.getAddress() );
        companyResponse.setCompanyId( company.getCompanyId() );
        companyResponse.setCreatedAt( company.getCreatedAt() );
        companyResponse.setEmail( company.getEmail() );
        companyResponse.setName( company.getName() );
        companyResponse.setPhone( company.getPhone() );
        companyResponse.setStatus( company.getStatus() );
        companyResponse.setTaxCode( company.getTaxCode() );
        companyResponse.setUpdatedAt( company.getUpdatedAt() );

        return companyResponse;
    }

    @Override
    public void updateCompany(Company company, CompanyUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        company.setAddress( request.getAddress() );
        company.setEmail( request.getEmail() );
        company.setName( request.getName() );
        company.setPhone( request.getPhone() );
        company.setStatus( request.getStatus() );
        company.setTaxCode( request.getTaxCode() );
    }
}
