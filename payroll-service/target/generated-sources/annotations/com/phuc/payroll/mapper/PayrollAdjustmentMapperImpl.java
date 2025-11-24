package com.phuc.payroll.mapper;

import com.phuc.payroll.dto.request.PayrollAdjustmentCreateRequest;
import com.phuc.payroll.dto.request.PayrollAdjustmentUpdateRequest;
import com.phuc.payroll.dto.response.PayrollAdjustmentResponse;
import com.phuc.payroll.entity.PayrollAdjustment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PayrollAdjustmentMapperImpl implements PayrollAdjustmentMapper {

    @Override
    public PayrollAdjustment toPayrollAdjustment(PayrollAdjustmentCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        PayrollAdjustment.PayrollAdjustmentBuilder payrollAdjustment = PayrollAdjustment.builder();

        payrollAdjustment.adjustmentType( request.getAdjustmentType() );
        payrollAdjustment.amount( request.getAmount() );
        payrollAdjustment.description( request.getDescription() );
        payrollAdjustment.effectiveDate( request.getEffectiveDate() );
        payrollAdjustment.payrollId( request.getPayrollId() );
        payrollAdjustment.userId( request.getUserId() );

        return payrollAdjustment.build();
    }

    @Override
    public PayrollAdjustmentResponse toPayrollAdjustmentResponse(PayrollAdjustment payrollAdjustment) {
        if ( payrollAdjustment == null ) {
            return null;
        }

        PayrollAdjustmentResponse payrollAdjustmentResponse = new PayrollAdjustmentResponse();

        payrollAdjustmentResponse.setAdjustmentId( payrollAdjustment.getAdjustmentId() );
        payrollAdjustmentResponse.setAdjustmentType( payrollAdjustment.getAdjustmentType() );
        payrollAdjustmentResponse.setAmount( payrollAdjustment.getAmount() );
        payrollAdjustmentResponse.setCreatedAt( payrollAdjustment.getCreatedAt() );
        payrollAdjustmentResponse.setDescription( payrollAdjustment.getDescription() );
        payrollAdjustmentResponse.setEffectiveDate( payrollAdjustment.getEffectiveDate() );
        payrollAdjustmentResponse.setPayrollId( payrollAdjustment.getPayrollId() );
        payrollAdjustmentResponse.setUpdatedAt( payrollAdjustment.getUpdatedAt() );
        payrollAdjustmentResponse.setUserId( payrollAdjustment.getUserId() );

        return payrollAdjustmentResponse;
    }

    @Override
    public void updatePayrollAdjustment(PayrollAdjustment payrollAdjustment, PayrollAdjustmentUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        payrollAdjustment.setAdjustmentType( request.getAdjustmentType() );
        payrollAdjustment.setAmount( request.getAmount() );
        payrollAdjustment.setDescription( request.getDescription() );
        payrollAdjustment.setEffectiveDate( request.getEffectiveDate() );
    }
}
