package com.phuc.hr.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionResponse {
    Long positionId;
    Long departmentId;
    String name;
    String description;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}


