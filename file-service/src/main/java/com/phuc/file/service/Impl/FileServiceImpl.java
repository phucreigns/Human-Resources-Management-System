package com.phuc.file.service.Impl;

import com.phuc.file.dto.request.UploadFileRequest;
import com.phuc.file.dto.response.FileResponse;
import com.phuc.file.entity.File;
import com.phuc.file.enums.DocumentType;
import com.phuc.file.mapper.FileMapper;
import com.phuc.file.repository.FileRepository;
import com.phuc.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import com.phuc.file.exception.AppException;
import com.phuc.file.exception.ErrorCode;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileServiceImpl implements FileService {

      S3Client s3Client;
      FileMapper fileMapper;
      FileRepository fileRepository;

      @Value("${cloud.aws.s3.bucket}")
      @NonFinal
      String bucketName;

      @Value("${cloud.aws.region.static}")
      @NonFinal
      String region;

      @Override
      public FileResponse uploadFile(MultipartFile file) throws IOException {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            uploadToS3(file, fileName);

            String fileUrl = generateFileUrl(fileName);
            File fileEntity = saveFileMetadata(file, fileName, fileUrl);


            fileEntity.setUrl(fileUrl);
            fileRepository.save(fileEntity);

            return fileMapper.toFileResponse(fileEntity);
      }

      @Override
      public List<FileResponse> uploadMultipleFiles(List<MultipartFile> files) throws IOException {          
            List<FileResponse> responses = new ArrayList<>();

            files.parallelStream().forEach(file -> {    
                  try {
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                        uploadToS3(file, fileName);

                        String fileUrl = generateFileUrl(fileName);
                        File fileEntity = saveFileMetadata(file, fileName, fileUrl);

                        fileEntity.setUrl(fileUrl);
                        fileRepository.save(fileEntity);

                        synchronized (responses) {
                              responses.add(fileMapper.toFileResponse(fileEntity));
                        }
                  } catch (IOException e) {
                        log.error("Failed to upload file: {}", file.getOriginalFilename(), e);
                  }
            });

            return responses;
      }

      @Override
      @PreAuthorize("hasRole('ADMIN')")
      public void deleteFile(String fileName) {
            s3Client.deleteObject(deleteRequest -> deleteRequest.bucket(bucketName).key(fileName));
            fileRepository.deleteByName(fileName);
      }

      @Override
      @PreAuthorize("hasRole('ADMIN')")
      public FileResponse getFile(String fileName) {
            File fileEntity = fileRepository.findByName(fileName)
                    .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
            return fileMapper.toFileResponse(fileEntity);
      }

      // HRMS Document Methods
      @Override
      public FileResponse uploadDocument(MultipartFile file, UploadFileRequest request, String uploadedBy) throws IOException {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            uploadToS3(file, fileName);

            String fileUrl = generateFileUrl(fileName);
            File fileEntity = File.builder()
                    .fileId(UUID.randomUUID().toString())
                    .name(fileName)
                    .url(fileUrl)
                    .size(String.valueOf(file.getSize()))
                    .type(file.getContentType())
                    .documentType(request.getDocumentType())
                    .employeeId(request.getEmployeeId())
                    .uploadedBy(uploadedBy)
                    .description(request.getDescription())
                    .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            fileRepository.save(fileEntity);
            log.info("Document uploaded: {} for employee: {} with type: {}", fileName, request.getEmployeeId(), request.getDocumentType());

            return fileMapper.toFileResponse(fileEntity);
      }

      @Override
      public List<FileResponse> getFilesByEmployeeId(String employeeId) {
            List<File> files = fileRepository.findByEmployeeId(employeeId);
            return files.stream()
                    .map(fileMapper::toFileResponse)
                    .toList();
      }

      @Override
      public List<FileResponse> getFilesByEmployeeIdAndStatus(String employeeId, String status) {
            List<File> files = fileRepository.findByEmployeeIdAndStatus(employeeId, status);
            return files.stream()
                    .map(fileMapper::toFileResponse)
                    .toList();
      }

      @Override
      public List<FileResponse> getFilesByDocumentType(DocumentType documentType) {
            List<File> files = fileRepository.findByDocumentType(documentType);
            return files.stream()
                    .map(fileMapper::toFileResponse)
                    .toList();
      }

      @Override
      public List<FileResponse> getFilesByEmployeeIdAndDocumentType(String employeeId, DocumentType documentType) {
            List<File> files = fileRepository.findByEmployeeIdAndDocumentType(employeeId, documentType);
            return files.stream()
                    .map(fileMapper::toFileResponse)
                    .toList();
      }

      @Override
      public List<FileResponse> getFilesByEmployeeIdAndDocumentTypeAndStatus(String employeeId, DocumentType documentType, String status) {
            List<File> files = fileRepository.findByEmployeeIdAndDocumentTypeAndStatus(employeeId, documentType, status);
            return files.stream()
                    .map(fileMapper::toFileResponse)
                    .toList();
      }

      private String generateFileUrl(String fileName) {
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
      }

      private void uploadToS3(MultipartFile file, String fileName) throws IOException {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
      }

      private File saveFileMetadata(MultipartFile file, String fileName, String fileUrl) {
            File fileEntity = File.builder()
                    .fileId(UUID.randomUUID().toString())
                    .name(fileName)
                    .url(fileUrl)
                    .size(String.valueOf(file.getSize()))
                    .type(file.getContentType())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            fileRepository.save(fileEntity);
            return fileEntity;
      }

}