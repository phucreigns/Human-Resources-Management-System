package com.phuc.auth.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long userId;
    Long companyId;
    Long departmentId;
    Long positionId;
    Long managerId;
    String employeeCode;
    String fullName;
    String email;   
    String auth0Id;
    String phoneNumber;
    String avatarUrl;
    String address;
    LocalDate hireDate;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

