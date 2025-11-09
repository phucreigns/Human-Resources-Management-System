package com.phuc.hr.mapper;

import com.phuc.hr.dto.request.PositionCreateRequest;
import com.phuc.hr.dto.request.PositionUpdateRequest;
import com.phuc.hr.dto.response.PositionResponse;
import com.phuc.hr.entity.Position;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PositionMapperImpl implements PositionMapper {

    @Override
    public Position toPosition(PositionCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Position.PositionBuilder position = Position.builder();

        position.departmentId( request.getDepartmentId() );
        position.description( request.getDescription() );
        position.name( request.getName() );
        position.status( request.getStatus() );

        return position.build();
    }

    @Override
    public PositionResponse toPositionResponse(Position position) {
        if ( position == null ) {
            return null;
        }

        PositionResponse positionResponse = new PositionResponse();

        positionResponse.setCreatedAt( position.getCreatedAt() );
        positionResponse.setDepartmentId( position.getDepartmentId() );
        positionResponse.setDescription( position.getDescription() );
        positionResponse.setName( position.getName() );
        positionResponse.setPositionId( position.getPositionId() );
        positionResponse.setStatus( position.getStatus() );
        positionResponse.setUpdatedAt( position.getUpdatedAt() );

        return positionResponse;
    }

    @Override
    public void updatePosition(Position position, PositionUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        position.setDescription( request.getDescription() );
        position.setName( request.getName() );
        position.setStatus( request.getStatus() );
    }
}
