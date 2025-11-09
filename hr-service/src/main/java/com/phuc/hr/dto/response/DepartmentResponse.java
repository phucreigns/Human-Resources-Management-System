package com.phuc.hr.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    Long departmentId;
    Long companyId;
    String name;
    String description;
    Long managerId;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}


