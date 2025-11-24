package com.phuc.hr.service.Impl;

import com.phuc.hr.dto.request.DepartmentCreateRequest;
import com.phuc.hr.dto.request.DepartmentUpdateRequest;
import com.phuc.hr.dto.response.DepartmentResponse;
import com.phuc.hr.entity.Department;
import com.phuc.hr.exception.AppException;
import com.phuc.hr.exception.ErrorCode;
import com.phuc.hr.mapper.DepartmentMapper;
import com.phuc.hr.repository.CompanyRepository;
import com.phuc.hr.repository.DepartmentRepository;
import com.phuc.hr.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    DepartmentRepository departmentRepository;
    CompanyRepository companyRepository;
    DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDepartmentResponse)
                .toList();
    }

    @Override
    public List<DepartmentResponse> getDepartmentsByCompanyId(Long companyId) {
        companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        List<Department> departments = departmentRepository.findByCompanyId(companyId);
        return departments.stream()
                .map(departmentMapper::toDepartmentResponse)
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        return departmentMapper.toDepartmentResponse(department);
    }

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentCreateRequest request) {
        // Verify company exists
        companyRepository.findByCompanyId(request.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        Department department = departmentMapper.toDepartment(request);
        Department savedDepartment = departmentRepository.save(department);
        log.info("Created department with ID: {}", savedDepartment.getDepartmentId());
        return departmentMapper.toDepartmentResponse(savedDepartment);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(Long departmentId, DepartmentUpdateRequest request) {
        Department department = departmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        departmentMapper.updateDepartment(department, request);
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Updated department with ID: {}", departmentId);
        return departmentMapper.toDepartmentResponse(updatedDepartment);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        departmentRepository.delete(department);
        log.info("Deleted department with ID: {}", departmentId);
    }
}


