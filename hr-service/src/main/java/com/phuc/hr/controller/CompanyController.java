package com.phuc.hr.controller;

import com.phuc.hr.dto.ApiResponse;
import com.phuc.hr.dto.request.CompanyCreateRequest;
import com.phuc.hr.dto.request.CompanyUpdateRequest;
import com.phuc.hr.dto.response.CompanyResponse;
import com.phuc.hr.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyController {

    CompanyService companyService;

    @GetMapping
    public ApiResponse<List<CompanyResponse>> getAllCompanies() {
        return ApiResponse.<List<CompanyResponse>>builder()
                .result(companyService.getAllCompanies())
                .message("Companies retrieved successfully")
                .build();
    }

    @GetMapping("/{companyId}")
    public ApiResponse<CompanyResponse> getCompanyById(@PathVariable Long companyId) {
        return ApiResponse.<CompanyResponse>builder()
                .result(companyService.getCompanyById(companyId))
                .message("Company retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CompanyResponse> createCompany(@RequestBody @Valid CompanyCreateRequest request) {
        return ApiResponse.<CompanyResponse>builder()
                .result(companyService.createCompany(request))
                .message("Company created successfully")
                .build();
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CompanyResponse> updateCompany(
            @PathVariable Long companyId,
            @RequestBody @Valid CompanyUpdateRequest request) {
        return ApiResponse.<CompanyResponse>builder()
                .result(companyService.updateCompany(companyId, request))
                .message("Company updated successfully")
                .build();
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


