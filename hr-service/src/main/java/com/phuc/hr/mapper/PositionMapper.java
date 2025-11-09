package com.phuc.hr.mapper;

import com.phuc.hr.dto.request.PositionCreateRequest;
import com.phuc.hr.dto.request.PositionUpdateRequest;
import com.phuc.hr.dto.response.PositionResponse;
import com.phuc.hr.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PositionMapper {
    Position toPosition(PositionCreateRequest request);
    PositionResponse toPositionResponse(Position position);
    void updatePosition(@MappingTarget Position position, PositionUpdateRequest request);
}


