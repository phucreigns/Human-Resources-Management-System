package com.phuc.hr.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyUpdateRequest {

    String name;
    String address;
    String phone;

    @Email(message = "Invalid email format")
    String email;

    String taxCode;
    String status;
}


