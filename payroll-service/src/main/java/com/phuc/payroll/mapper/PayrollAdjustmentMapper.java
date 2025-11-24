package com.phuc.payroll.mapper;

import com.phuc.payroll.dto.request.PayrollAdjustmentCreateRequest;
import com.phuc.payroll.dto.request.PayrollAdjustmentUpdateRequest;
import com.phuc.payroll.dto.response.PayrollAdjustmentResponse;
import com.phuc.payroll.entity.PayrollAdjustment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayrollAdjustmentMapper {
    PayrollAdjustment toPayrollAdjustment(PayrollAdjustmentCreateRequest request);
    PayrollAdjustmentResponse toPayrollAdjustmentResponse(PayrollAdjustment payrollAdjustment);
    void updatePayrollAdjustment(@MappingTarget PayrollAdjustment payrollAdjustment, PayrollAdjustmentUpdateRequest request);
}





