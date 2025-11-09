package com.phuc.hr.controller;

import com.phuc.hr.dto.ApiResponse;
import com.phuc.hr.dto.request.DepartmentCreateRequest;
import com.phuc.hr.dto.request.DepartmentUpdateRequest;
import com.phuc.hr.dto.response.DepartmentResponse;
import com.phuc.hr.service.DepartmentService;
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
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

    DepartmentService departmentService;

    @GetMapping
    public ApiResponse<List<DepartmentResponse>> getAllDepartments() {
        return ApiResponse.<List<DepartmentResponse>>builder()
                .result(departmentService.getAllDepartments())
                .message("Departments retrieved successfully")
                .build();
    }

    @GetMapping("/company/{companyId}")
    public ApiResponse<List<DepartmentResponse>> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        return ApiResponse.<List<DepartmentResponse>>builder()
                .result(departmentService.getDepartmentsByCompanyId(companyId))
                .message("Departments retrieved successfully")
                .build();
    }

    @GetMapping("/{departmentId}")
    public ApiResponse<DepartmentResponse> getDepartmentById(@PathVariable Long departmentId) {
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.getDepartmentById(departmentId))
                .message("Department retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DepartmentResponse> createDepartment(@RequestBody @Valid DepartmentCreateRequest request) {
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.createDepartment(request))
                .message("Department created successfully")
                .build();
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DepartmentResponse> updateDepartment(
            @PathVariable Long departmentId,
            @RequestBody @Valid DepartmentUpdateRequest request) {
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.updateDepartment(departmentId, request))
                .message("Department updated successfully")
                .build();
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


