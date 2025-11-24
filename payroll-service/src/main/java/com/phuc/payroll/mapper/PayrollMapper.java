package com.phuc.payroll.mapper;

import com.phuc.payroll.dto.request.PayrollCreateRequest;
import com.phuc.payroll.dto.request.PayrollUpdateRequest;
import com.phuc.payroll.dto.response.PayrollResponse;
import com.phuc.payroll.entity.Payroll;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayrollMapper {
    Payroll toPayroll(PayrollCreateRequest request);
    PayrollResponse toPayrollResponse(Payroll payroll);
    void updatePayroll(@MappingTarget Payroll payroll, PayrollUpdateRequest request);
}





