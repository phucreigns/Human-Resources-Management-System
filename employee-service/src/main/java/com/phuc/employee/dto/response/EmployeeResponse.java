package com.phuc.employee.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    Long employeeId;
    Long companyId;
    Long departmentId;
    Long positionId;
    Long managerId;
    String employeeCode;
    String fullName;
    String email;   
    String auth0Id;
    String phone;
    LocalDate hireDate;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
