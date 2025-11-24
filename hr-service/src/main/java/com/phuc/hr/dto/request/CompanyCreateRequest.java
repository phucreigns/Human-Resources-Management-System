package com.phuc.hr.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyCreateRequest {

    @NotBlank(message = "Company name is required")
    String name;

    String address;

    @NotNull(message = "Phone is required")
    String phone;

    @Email(message = "Invalid email format")
    String email;

    String taxCode;

    String status;
}


