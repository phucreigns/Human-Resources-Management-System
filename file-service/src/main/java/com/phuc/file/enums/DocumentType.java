package com.phuc.file.enums;

public enum DocumentType {
    // Hồ sơ nhân viên
    EMPLOYEE_PROFILE("Hồ sơ nhân viên"),
    
    // Hồ sơ hợp đồng
    CONTRACT("Hồ sơ hợp đồng"),
    CONTRACT_EXTENSION("Gia hạn hợp đồng"),
    CONTRACT_TERMINATION("Chấm dứt hợp đồng"),
    
    // CV và Resume
    CV("CV/Resume"),
    RESUME("Sơ yếu lý lịch"),
    
    // Hồ sơ xin nghỉ việc
    RESIGNATION("Hồ sơ xin nghỉ việc"),
    RESIGNATION_APPROVAL("Phê duyệt nghỉ việc"),
    
    // Tài liệu khác
    ID_CARD("CMND/CCCD"),
    DEGREE("Bằng cấp"),
    CERTIFICATE("Chứng chỉ"),
    MEDICAL_RECORD("Hồ sơ y tế"),
    PAYROLL_DOCUMENT("Tài liệu lương"),
    TAX_DOCUMENT("Tài liệu thuế"),
    INSURANCE_DOCUMENT("Tài liệu bảo hiểm"),
    
    // Hình ảnh
    AVATAR("Ảnh đại diện"),
    PROFILE_PHOTO("Ảnh hồ sơ"),
    
    // Tài liệu khác
    OTHER("Tài liệu khác");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}



