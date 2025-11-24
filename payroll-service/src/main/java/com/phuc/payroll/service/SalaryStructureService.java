package com.phuc.payroll.service;

import com.phuc.payroll.dto.request.SalaryStructureCreateRequest;
import com.phuc.payroll.dto.request.SalaryStructureUpdateRequest;
import com.phuc.payroll.dto.response.SalaryStructureResponse;

import java.util.List;

public interface SalaryStructureService {
    List<SalaryStructureResponse> getAllSalaryStructures();
    SalaryStructureResponse getSalaryStructureById(Long salaryStructureId);
    List<SalaryStructureResponse> getSalaryStructuresByUserId(Long userId);
    SalaryStructureResponse getActiveSalaryStructureByUserId(Long userId);
    SalaryStructureResponse createSalaryStructure(SalaryStructureCreateRequest request);
    SalaryStructureResponse updateSalaryStructure(Long salaryStructureId, SalaryStructureUpdateRequest request);
    void deleteSalaryStructure(Long salaryStructureId);
}

