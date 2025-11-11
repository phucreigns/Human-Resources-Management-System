package com.phuc.file.dto.request;

import com.phuc.file.enums.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileRequest {
    
    @NotNull(message = "DOCUMENT_TYPE_IS_REQUIRED")
    DocumentType documentType;
    
    String employeeId; // ID nhân viên (bắt buộc cho tài liệu HRMS)
    
    String description; // Mô tả tài liệu
    
    String status; // Trạng thái mặc định: ACTIVE
}



