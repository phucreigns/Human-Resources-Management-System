package com.phuc.payroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    Long userId;           
    Long companyId;       
    Long departmentId;
    Long positionId;
    Long managerId;
    String fullName; 
    String email;
    String employeeCode; 
    String auth0Id;
    String phoneNumber;
    String avatarUrl;
    String address;
    String hireDate;  
    String status;
    String createdAt;
    String updatedAt;

}
