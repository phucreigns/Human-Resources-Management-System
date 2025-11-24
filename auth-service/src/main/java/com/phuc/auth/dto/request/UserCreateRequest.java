package com.phuc.auth.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotNull(message = "Company id cannot be null")
    Long companyId;

    Long departmentId;

    Long positionId;
    
    Long managerId;

    String employeeCode;

    @NotNull(message = "Full name cannot be null")
    String fullName;

    @NotNull(message = "Email cannot be null")
    String email;

    @NotNull(message = "Phone number cannot be null")
    String phone;

    @NotNull(message = "Hire date cannot be null")
    LocalDate hireDate;

    String status;
}

