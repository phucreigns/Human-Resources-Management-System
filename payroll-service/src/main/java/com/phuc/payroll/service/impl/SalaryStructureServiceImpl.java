package com.phuc.payroll.service.impl;

import com.phuc.payroll.dto.request.SalaryStructureCreateRequest;
import com.phuc.payroll.dto.request.SalaryStructureUpdateRequest;
import com.phuc.payroll.dto.response.SalaryStructureResponse;
import com.phuc.payroll.entity.SalaryStructure;
import com.phuc.payroll.exception.AppException;
import com.phuc.payroll.exception.ErrorCode;
import com.phuc.payroll.mapper.SalaryStructureMapper;
import com.phuc.payroll.repository.SalaryStructureRepository;
import com.phuc.payroll.service.SalaryStructureService;
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
public class SalaryStructureServiceImpl implements SalaryStructureService {

    SalaryStructureRepository salaryStructureRepository;
    SalaryStructureMapper salaryStructureMapper;

    @Override
    public List<SalaryStructureResponse> getAllSalaryStructures() {
        List<SalaryStructure> salaryStructures = salaryStructureRepository.findAll();
        return salaryStructures.stream()
                .map(salaryStructureMapper::toSalaryStructureResponse)
                .toList();
    }

    @Override
    public SalaryStructureResponse getSalaryStructureById(Long salaryStructureId) {
        SalaryStructure salaryStructure = salaryStructureRepository.findBySalaryStructureId(salaryStructureId)
                .orElseThrow(() -> new AppException(ErrorCode.SALARY_STRUCTURE_NOT_FOUND));
        return salaryStructureMapper.toSalaryStructureResponse(salaryStructure);
    }

    @Override
    public List<SalaryStructureResponse> getSalaryStructuresByUserId(Long userId) {
        List<SalaryStructure> salaryStructures = salaryStructureRepository.findByUserId(userId);
        return salaryStructures.stream()
                .map(salaryStructureMapper::toSalaryStructureResponse)
                .toList();
    }

    @Override
    public SalaryStructureResponse getActiveSalaryStructureByUserId(Long userId) {
        SalaryStructure salaryStructure = salaryStructureRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SALARY_STRUCTURE_NOT_FOUND));
        return salaryStructureMapper.toSalaryStructureResponse(salaryStructure);
    }

    @Override
    @Transactional
    public SalaryStructureResponse createSalaryStructure(SalaryStructureCreateRequest request) {
        SalaryStructure salaryStructure = salaryStructureMapper.toSalaryStructure(request);
        SalaryStructure savedSalaryStructure = salaryStructureRepository.save(salaryStructure);
        log.info("Created salary structure with ID: {}", savedSalaryStructure.getSalaryStructureId());
        return salaryStructureMapper.toSalaryStructureResponse(savedSalaryStructure);
    }

    @Override
    @Transactional
    public SalaryStructureResponse updateSalaryStructure(Long salaryStructureId, SalaryStructureUpdateRequest request) {
        SalaryStructure salaryStructure = salaryStructureRepository.findBySalaryStructureId(salaryStructureId)
                .orElseThrow(() -> new AppException(ErrorCode.SALARY_STRUCTURE_NOT_FOUND));

        salaryStructureMapper.updateSalaryStructure(salaryStructure, request);
        SalaryStructure updatedSalaryStructure = salaryStructureRepository.save(salaryStructure);
        log.info("Updated salary structure with ID: {}", salaryStructureId);
        return salaryStructureMapper.toSalaryStructureResponse(updatedSalaryStructure);
    }

    @Override
    @Transactional
    public void deleteSalaryStructure(Long salaryStructureId) {
        SalaryStructure salaryStructure = salaryStructureRepository.findBySalaryStructureId(salaryStructureId)
                .orElseThrow(() -> new AppException(ErrorCode.SALARY_STRUCTURE_NOT_FOUND));
        salaryStructureRepository.delete(salaryStructure);
        log.info("Deleted salary structure with ID: {}", salaryStructureId);
    }
}

