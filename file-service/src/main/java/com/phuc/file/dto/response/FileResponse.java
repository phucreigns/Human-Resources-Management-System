package com.phuc.file.dto.response;

import com.phuc.file.enums.DocumentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileResponse {
    String fileId;
    String name;
    String type;
    String url;
    String size;
    
    // HRMS Document Fields
    DocumentType documentType;
    String employeeId;
    String uploadedBy;
    String description;
    String status;
    
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
