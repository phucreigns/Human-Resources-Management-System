package com.phuc.hr.service.Impl;

import com.phuc.hr.dto.request.PositionCreateRequest;
import com.phuc.hr.dto.request.PositionUpdateRequest;
import com.phuc.hr.dto.response.PositionResponse;
import com.phuc.hr.entity.Position;
import com.phuc.hr.exception.AppException;
import com.phuc.hr.exception.ErrorCode;
import com.phuc.hr.mapper.PositionMapper;
import com.phuc.hr.repository.DepartmentRepository;
import com.phuc.hr.repository.PositionRepository;
import com.phuc.hr.service.PositionService;
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
public class PositionServiceImpl implements PositionService {

    PositionRepository positionRepository;
    DepartmentRepository departmentRepository;
    PositionMapper positionMapper;

    @Override
    public List<PositionResponse> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        return positions.stream()
                .map(positionMapper::toPositionResponse)
                .toList();
    }

    @Override
    public List<PositionResponse> getPositionsByDepartmentId(Long departmentId) {
        departmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        List<Position> positions = positionRepository.findByDepartmentId(departmentId);
        return positions.stream()
                .map(positionMapper::toPositionResponse)
                .toList();
    }

    @Override
    public PositionResponse getPositionById(Long positionId) {
        Position position = positionRepository.findByPositionId(positionId)
                .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));
        return positionMapper.toPositionResponse(position);
    }

    @Override
    @Transactional
    public PositionResponse createPosition(PositionCreateRequest request) {
        departmentRepository.findByDepartmentId(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        Position position = positionMapper.toPosition(request);
        Position savedPosition = positionRepository.save(position);
        log.info("Created position with ID: {}", savedPosition.getPositionId());
        return positionMapper.toPositionResponse(savedPosition);
    }

    @Override
    @Transactional
    public PositionResponse updatePosition(Long positionId, PositionUpdateRequest request) {
        Position position = positionRepository.findByPositionId(positionId)
                .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));

        positionMapper.updatePosition(position, request);
        Position updatedPosition = positionRepository.save(position);
        log.info("Updated position with ID: {}", positionId);
        return positionMapper.toPositionResponse(updatedPosition);
    }

    @Override
    @Transactional
    public void deletePosition(Long positionId) {
        Position position = positionRepository.findByPositionId(positionId)
                .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));
        positionRepository.delete(position);
        log.info("Deleted position with ID: {}", positionId);
    }
}


