package com.phuc.file.repository;

import com.phuc.file.entity.File;
import com.phuc.file.enums.DocumentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface FileRepository extends MongoRepository<File, String> {

    void deleteByName(String fileName);

    Optional<File> findByName(String fileName);
    
    // HRMS Document Queries
    List<File> findByEmployeeId(String employeeId);
    
    List<File> findByEmployeeIdAndStatus(String employeeId, String status);
    
    List<File> findByDocumentType(DocumentType documentType);
    
    List<File> findByEmployeeIdAndDocumentType(String employeeId, DocumentType documentType);
    
    List<File> findByEmployeeIdAndDocumentTypeAndStatus(String employeeId, DocumentType documentType, String status);

}
