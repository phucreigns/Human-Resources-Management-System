package com.phuc.payroll.exception;

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

    SALARY_STRUCTURE_NOT_FOUND(4000, "Salary structure not found. Please ensure the salary structure exists.", HttpStatus.NOT_FOUND),
    PAYROLL_NOT_FOUND(4001, "Payroll not found. Please ensure the payroll exists.", HttpStatus.NOT_FOUND),
    PAYROLL_ADJUSTMENT_NOT_FOUND(4002, "Payroll adjustment not found. Please ensure the payroll adjustment exists.", HttpStatus.NOT_FOUND),
    INVALID_PAYROLL_PERIOD(4003, "Invalid payroll period. Please provide a valid period.", HttpStatus.BAD_REQUEST),
    INVALID_ADJUSTMENT_TYPE(4004, "Invalid adjustment type. Please provide a valid adjustment type.", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode statusCode;

}





