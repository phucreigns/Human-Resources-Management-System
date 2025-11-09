package com.phuc.hr.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentUpdateRequest {

    String name;
    String description;
    Long managerId;
    String status;
}


