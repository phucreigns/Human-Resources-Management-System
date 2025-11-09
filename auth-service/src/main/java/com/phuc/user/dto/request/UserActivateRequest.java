package com.phuc.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserActivateRequest {
    
    @NotNull(message = "Company ID is required")
    Long companyId;
    
    String status; // Optional: Default "ACTIVE" if not provided
}

