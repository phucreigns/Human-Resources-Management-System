package com.phuc.file.entity;

import com.phuc.file.enums.DocumentType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "files")
public class File {

    @Id
    String fileId;

    String name;

    String type; // MIME type (image/jpeg, application/pdf, etc.)

    String url;

    String size;

    // HRMS Document Fields
    DocumentType documentType; // Loại tài liệu (EMPLOYEE_PROFILE, CONTRACT, CV, etc.)
    
    String employeeId; // ID nhân viên (liên kết với HR service)
    
    String uploadedBy; // User ID của người upload
    
    String description; // Mô tả tài liệu
    
    String status; // Trạng thái: ACTIVE, ARCHIVED, DELETED

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
