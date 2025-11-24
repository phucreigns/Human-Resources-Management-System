package com.phuc.payroll.controller;

import com.phuc.payroll.dto.ApiResponse;
import com.phuc.payroll.dto.request.SalaryStructureCreateRequest;
import com.phuc.payroll.dto.request.SalaryStructureUpdateRequest;
import com.phuc.payroll.dto.response.SalaryStructureResponse;
import com.phuc.payroll.service.SalaryStructureService;
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
@RequestMapping("/salary-structures")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryStructureController {

    SalaryStructureService salaryStructureService;

    @GetMapping
    public ApiResponse<List<SalaryStructureResponse>> getAllSalaryStructures() {
        return ApiResponse.<List<SalaryStructureResponse>>builder()
                .result(salaryStructureService.getAllSalaryStructures())
                .message("Salary structures retrieved successfully")
                .build();
    }

    @GetMapping("/{salaryStructureId}")
    public ApiResponse<SalaryStructureResponse> getSalaryStructureById(@PathVariable Long salaryStructureId) {
        return ApiResponse.<SalaryStructureResponse>builder()
                .result(salaryStructureService.getSalaryStructureById(salaryStructureId))
                .message("Salary structure retrieved successfully")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<SalaryStructureResponse>> getSalaryStructuresByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<SalaryStructureResponse>>builder()
                .result(salaryStructureService.getSalaryStructuresByUserId(userId))
                .message("Salary structures retrieved successfully")
                .build();
    }

    @GetMapping("/user/{userId}/active")
    public ApiResponse<SalaryStructureResponse> getActiveSalaryStructureByUserId(@PathVariable Long userId) {
        return ApiResponse.<SalaryStructureResponse>builder()
                .result(salaryStructureService.getActiveSalaryStructureByUserId(userId))
                .message("Active salary structure retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SalaryStructureResponse> createSalaryStructure(@RequestBody @Valid SalaryStructureCreateRequest request) {
        return ApiResponse.<SalaryStructureResponse>builder()
                .result(salaryStructureService.createSalaryStructure(request))
                .message("Salary structure created successfully")
                .build();
    }

    @PutMapping("/{salaryStructureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SalaryStructureResponse> updateSalaryStructure(
            @PathVariable Long salaryStructureId,
            @RequestBody @Valid SalaryStructureUpdateRequest request) {
        return ApiResponse.<SalaryStructureResponse>builder()
                .result(salaryStructureService.updateSalaryStructure(salaryStructureId, request))
                .message("Salary structure updated successfully")
                .build();
    }

    @DeleteMapping("/{salaryStructureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSalaryStructure(@PathVariable Long salaryStructureId) {
        salaryStructureService.deleteSalaryStructure(salaryStructureId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

