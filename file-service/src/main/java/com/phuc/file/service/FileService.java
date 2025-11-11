package com.phuc.file.service;

import com.phuc.file.dto.request.UploadFileRequest;
import com.phuc.file.dto.response.FileResponse;
import com.phuc.file.enums.DocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileResponse uploadFile(MultipartFile file) throws IOException;

    List<FileResponse> uploadMultipleFiles(List<MultipartFile> files) throws IOException;
    
    // HRMS Document Methods
    FileResponse uploadDocument(MultipartFile file, UploadFileRequest request, String uploadedBy) throws IOException;
    
    List<FileResponse> getFilesByEmployeeId(String employeeId);
    
    List<FileResponse> getFilesByEmployeeIdAndStatus(String employeeId, String status);
    
    List<FileResponse> getFilesByDocumentType(DocumentType documentType);
    
    List<FileResponse> getFilesByEmployeeIdAndDocumentType(String employeeId, DocumentType documentType);
    
    List<FileResponse> getFilesByEmployeeIdAndDocumentTypeAndStatus(String employeeId, DocumentType documentType, String status);

    void deleteFile(String fileName);

    FileResponse getFile(String fileName);
}