package com.phuc.hr.controller;

import com.phuc.hr.dto.ApiResponse;
import com.phuc.hr.dto.request.PositionCreateRequest;
import com.phuc.hr.dto.request.PositionUpdateRequest;
import com.phuc.hr.dto.response.PositionResponse;
import com.phuc.hr.service.PositionService;
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
@RequestMapping("/positions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {

    PositionService positionService;

    @GetMapping
    public ApiResponse<List<PositionResponse>> getAllPositions() {
        return ApiResponse.<List<PositionResponse>>builder()
                .result(positionService.getAllPositions())
                .message("Positions retrieved successfully")
                .build();
    }

    @GetMapping("/department/{departmentId}")
    public ApiResponse<List<PositionResponse>> getPositionsByDepartmentId(@PathVariable Long departmentId) {
        return ApiResponse.<List<PositionResponse>>builder()
                .result(positionService.getPositionsByDepartmentId(departmentId))
                .message("Positions retrieved successfully")
                .build();
    }

    @GetMapping("/{positionId}")
    public ApiResponse<PositionResponse> getPositionById(@PathVariable Long positionId) {
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.getPositionById(positionId))
                .message("Position retrieved successfully")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PositionResponse> createPosition(@RequestBody @Valid PositionCreateRequest request) {
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.createPosition(request))
                .message("Position created successfully")
                .build();
    }

    @PutMapping("/{positionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PositionResponse> updatePosition(
            @PathVariable Long positionId,
            @RequestBody @Valid PositionUpdateRequest request) {
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.updatePosition(positionId, request))
                .message("Position updated successfully")
                .build();
    }

    @DeleteMapping("/{positionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


