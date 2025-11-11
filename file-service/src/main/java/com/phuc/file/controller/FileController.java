package com.phuc.file.controller;

import com.phuc.file.dto.ApiResponse;
import com.phuc.file.dto.request.UploadFileRequest;
import com.phuc.file.dto.response.FileResponse;
import com.phuc.file.enums.DocumentType;
import com.phuc.file.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {
    FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileResponse response = fileService.uploadFile(file);
        return ApiResponse.<FileResponse>builder()
                .result(response)
                .message("File uploaded successfully")
                .build();
    }

    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<FileResponse>> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> files) throws IOException {
        List<FileResponse> responses = fileService.uploadMultipleFiles(files);
        return ApiResponse.<List<FileResponse>>builder()
                .result(responses)
                .message("Files uploaded successfully")
                .build();
    }

    @GetMapping("/{fileName}")
    public ApiResponse<FileResponse> getFile(@PathVariable String fileName) {
        FileResponse response = fileService.getFile(fileName);
        return ApiResponse.<FileResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/{fileName}")
    public ApiResponse<String> deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return ApiResponse.<String>builder()
                .result("File deleted successfully")
                .message("File deleted successfully")
                .build();
    }

    // HRMS Document Endpoints
    @PostMapping(value = "/documents/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") DocumentType documentType,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String status,
            Authentication authentication) throws IOException {
        String uploadedBy = getUserIdFromAuthentication(authentication);
        
        UploadFileRequest request = UploadFileRequest.builder()
                .documentType(documentType)
                .employeeId(employeeId)
                .description(description)
                .status(status != null ? status : "ACTIVE")
                .build();
        
        FileResponse response = fileService.uploadDocument(file, request, uploadedBy);
        return ApiResponse.<FileResponse>builder()
                .result(response)
                .message("Document uploaded successfully")
                .build();
    }

    @GetMapping("/employees/{employeeId}/documents")
    public ApiResponse<List<FileResponse>> getEmployeeDocuments(
            @PathVariable String employeeId,
            @RequestParam(required = false) DocumentType documentType,
            @RequestParam(required = false) String status) {
        List<FileResponse> responses;
        
        if (documentType != null && status != null) {
            responses = fileService.getFilesByEmployeeIdAndDocumentTypeAndStatus(employeeId, documentType, status);
        } else if (documentType != null) {
            responses = fileService.getFilesByEmployeeIdAndDocumentType(employeeId, documentType);
        } else if (status != null) {
            responses = fileService.getFilesByEmployeeIdAndStatus(employeeId, status);
        } else {
            responses = fileService.getFilesByEmployeeId(employeeId);
        }
        
        return ApiResponse.<List<FileResponse>>builder()
                .result(responses)
                .message("Documents retrieved successfully")
                .build();
    }

    @GetMapping("/documents")
    public ApiResponse<List<FileResponse>> getDocumentsByType(
            @RequestParam(required = false) DocumentType documentType) {
        List<FileResponse> responses;
        
        if (documentType != null) {
            responses = fileService.getFilesByDocumentType(documentType);
        } else {
            responses = List.of();
        }
        
        return ApiResponse.<List<FileResponse>>builder()
                .result(responses)
                .message("Documents retrieved successfully")
                .build();
    }

    private String getUserIdFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject(); // hoặc jwt.getClaim("sub")
        }
        return "system";
    }
}
