package com.phuc.hr.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentCreateRequest {

    @NotNull(message = "Company ID is required")
    Long companyId;

    @NotBlank(message = "Department name is required")
    String name;

    String description;

    Long managerId;

    String status;
}


