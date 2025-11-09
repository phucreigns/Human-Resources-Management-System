package com.phuc.hr.service;

import com.phuc.hr.dto.request.PositionCreateRequest;
import com.phuc.hr.dto.request.PositionUpdateRequest;
import com.phuc.hr.dto.response.PositionResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PositionService {
    List<PositionResponse> getAllPositions();
    List<PositionResponse> getPositionsByDepartmentId(Long departmentId);
    PositionResponse getPositionById(Long positionId);
    PositionResponse createPosition(PositionCreateRequest request);
    PositionResponse updatePosition(Long positionId, PositionUpdateRequest request);
    void deletePosition(Long positionId);
}


