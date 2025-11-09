package com.phuc.hr.service;

import com.phuc.hr.dto.request.DepartmentCreateRequest;
import com.phuc.hr.dto.request.DepartmentUpdateRequest;
import com.phuc.hr.dto.response.DepartmentResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAllDepartments();
    List<DepartmentResponse> getDepartmentsByCompanyId(Long companyId);
    DepartmentResponse getDepartmentById(Long departmentId);
    DepartmentResponse createDepartment(DepartmentCreateRequest request);
    DepartmentResponse updateDepartment(Long departmentId, DepartmentUpdateRequest request);
    void deleteDepartment(Long departmentId);
}


