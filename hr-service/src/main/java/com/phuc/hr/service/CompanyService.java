package com.phuc.hr.service;

import com.phuc.hr.dto.request.CompanyCreateRequest;
import com.phuc.hr.dto.request.CompanyUpdateRequest;
import com.phuc.hr.dto.response.CompanyResponse;
import java.util.List;

public interface CompanyService {
    List<CompanyResponse> getAllCompanies();
    CompanyResponse getCompanyById(Long companyId);
    CompanyResponse createCompany(CompanyCreateRequest request);
    CompanyResponse updateCompany(Long companyId, CompanyUpdateRequest request);
    void deleteCompany(Long companyId);
}


