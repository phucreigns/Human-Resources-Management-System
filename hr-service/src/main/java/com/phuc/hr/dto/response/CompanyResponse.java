package com.phuc.hr.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyResponse {
    Long companyId;
    String name;
    String address;
    String phone;
    String email;
    String taxCode;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}


