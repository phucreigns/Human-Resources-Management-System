package com.phuc.hr.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(1000, "An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "The provided key is invalid. Please check the key and try again.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1002, "You are not authenticated. Please log in first.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1003, "You are not authorized to perform this action.", HttpStatus.UNAUTHORIZED),
    DATA_INTEGRITY_VIOLATION(1005, "Data integrity violation. The operation cannot be completed due to conflicting data.", HttpStatus.BAD_REQUEST),
    DUPLICATE_ENTRY(1006, "The entry already exists. Please check for duplicates and try again.", HttpStatus.BAD_REQUEST),
    FOREIGN_KEY_VIOLATION(1007, "Foreign key violation. Please ensure all related data exists before proceeding.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_PARAMETER(1009, "A required parameter is missing. Please ensure all required fields are provided.", HttpStatus.BAD_REQUEST),

    COMPANY_NOT_FOUND(3000, "Company not found. Please ensure the company exists.", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND(3001, "Department not found. Please ensure the department exists.", HttpStatus.NOT_FOUND),
    POSITION_NOT_FOUND(3002, "Position not found. Please ensure the position exists.", HttpStatus.NOT_FOUND),
    TAX_CODE_EXISTED(3003, "Tax code already exists. Please use a different tax code.", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode statusCode;

}


