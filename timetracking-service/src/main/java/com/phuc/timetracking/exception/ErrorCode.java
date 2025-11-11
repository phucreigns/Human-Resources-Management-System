package com.phuc.timetracking.exception;

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

    ATTENDANCE_RECORD_NOT_FOUND(4000, "Attendance record not found. Please ensure the attendance record exists.", HttpStatus.NOT_FOUND),
    LEAVE_TYPE_NOT_FOUND(4001, "Leave type not found. Please ensure the leave type exists.", HttpStatus.NOT_FOUND),
    LEAVE_REQUEST_NOT_FOUND(4002, "Leave request not found. Please ensure the leave request exists.", HttpStatus.NOT_FOUND),
    LEAVE_TYPE_CODE_EXISTED(4003, "Leave type code already exists. Please use a different code.", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE(4004, "Invalid date range. End date must be after start date.", HttpStatus.BAD_REQUEST),
    INVALID_CHECK_OUT_TIME(4005, "Check-out time must be after check-in time.", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode statusCode;

}

