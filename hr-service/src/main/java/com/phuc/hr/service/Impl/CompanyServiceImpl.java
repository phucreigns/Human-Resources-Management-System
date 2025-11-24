package com.phuc.hr.service.Impl;

import com.phuc.hr.dto.request.CompanyCreateRequest;
import com.phuc.hr.dto.request.CompanyUpdateRequest;
import com.phuc.hr.dto.response.CompanyResponse;
import com.phuc.hr.entity.Company;
import com.phuc.hr.exception.AppException;
import com.phuc.hr.exception.ErrorCode;
import com.phuc.hr.mapper.CompanyMapper;
import com.phuc.hr.repository.CompanyRepository;
import com.phuc.hr.service.CompanyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyServiceImpl implements CompanyService {

    CompanyRepository companyRepository;
    CompanyMapper companyMapper;

    @Override
    public List<CompanyResponse> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(companyMapper::toCompanyResponse)
                .toList();
    }

    @Override
    public CompanyResponse getCompanyById(Long companyId) {
        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        return companyMapper.toCompanyResponse(company);
    }

    @Override
    @Transactional
    public CompanyResponse createCompany(CompanyCreateRequest request) {
        if (request.getTaxCode() != null && !request.getTaxCode().isEmpty()) {
            if (companyRepository.existsByTaxCode(request.getTaxCode())) {
                throw new AppException(ErrorCode.TAX_CODE_EXISTED);
            }
        }

        Company company = companyMapper.toCompany(request);
        Company savedCompany = companyRepository.save(company);
        log.info("Created company with ID: {}", savedCompany.getCompanyId());
        return companyMapper.toCompanyResponse(savedCompany);
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(Long companyId, CompanyUpdateRequest request) {
        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        if (request.getTaxCode() != null && !request.getTaxCode().isEmpty()) {
            companyRepository.findByTaxCode(request.getTaxCode())
                    .ifPresent(existingCompany -> {
                        if (!existingCompany.getCompanyId().equals(companyId)) {
                            throw new AppException(ErrorCode.TAX_CODE_EXISTED);
                        }
                    });
        }

        companyMapper.updateCompany(company, request);
        Company updatedCompany = companyRepository.save(company);
        log.info("Updated company with ID: {}", companyId);
        return companyMapper.toCompanyResponse(updatedCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        companyRepository.delete(company);
        log.info("Deleted company with ID: {}", companyId);
    }
}


