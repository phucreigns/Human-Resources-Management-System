package com.phuc.auth.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @NotNull(message = "Full name cannot be null")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    String fullName;

    @Size(max = 255, message = "Email must not exceed 255 characters")
    String email;

    @Size(max = 30, message = "Phone number must not exceed 30 characters")
    String phoneNumber;

    @Size(max = 255, message = "Avatar URL must not exceed 255 characters")
    String avatarUrl;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    String address;

    LocalDate hireDate;
    
    Long companyId;
    
    Long departmentId;
    
    Long positionId;
    
    Long managerId;
    
    @Size(max = 50, message = "Status must not exceed 50 characters")
    String status;
}

