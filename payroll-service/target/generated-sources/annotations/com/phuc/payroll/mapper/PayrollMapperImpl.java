package com.phuc.payroll.mapper;

import com.phuc.payroll.dto.request.PayrollCreateRequest;
import com.phuc.payroll.dto.request.PayrollUpdateRequest;
import com.phuc.payroll.dto.response.PayrollResponse;
import com.phuc.payroll.entity.Payroll;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PayrollMapperImpl implements PayrollMapper {

    @Override
    public Payroll toPayroll(PayrollCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Payroll.PayrollBuilder payroll = Payroll.builder();

        Map<String, Object> map = request.getBonuses();
        if ( map != null ) {
            payroll.bonuses( new LinkedHashMap<String, Object>( map ) );
        }
        Map<String, Object> map1 = request.getDeductions();
        if ( map1 != null ) {
            payroll.deductions( new LinkedHashMap<String, Object>( map1 ) );
        }
        payroll.grossSalary( request.getGrossSalary() );
        payroll.netSalary( request.getNetSalary() );
        payroll.payDate( request.getPayDate() );
        payroll.payrollPeriod( request.getPayrollPeriod() );
        payroll.status( request.getStatus() );
        payroll.userId( request.getUserId() );

        return payroll.build();
    }

    @Override
    public PayrollResponse toPayrollResponse(Payroll payroll) {
        if ( payroll == null ) {
            return null;
        }

        PayrollResponse payrollResponse = new PayrollResponse();

        Map<String, Object> map = payroll.getBonuses();
        if ( map != null ) {
            payrollResponse.setBonuses( new LinkedHashMap<String, Object>( map ) );
        }
        payrollResponse.setCreatedAt( payroll.getCreatedAt() );
        Map<String, Object> map1 = payroll.getDeductions();
        if ( map1 != null ) {
            payrollResponse.setDeductions( new LinkedHashMap<String, Object>( map1 ) );
        }
        payrollResponse.setGrossSalary( payroll.getGrossSalary() );
        payrollResponse.setNetSalary( payroll.getNetSalary() );
        payrollResponse.setPayDate( payroll.getPayDate() );
        payrollResponse.setPayrollId( payroll.getPayrollId() );
        payrollResponse.setPayrollPeriod( payroll.getPayrollPeriod() );
        payrollResponse.setStatus( payroll.getStatus() );
        payrollResponse.setUpdatedAt( payroll.getUpdatedAt() );
        payrollResponse.setUserId( payroll.getUserId() );

        return payrollResponse;
    }

    @Override
    public void updatePayroll(Payroll payroll, PayrollUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( payroll.getBonuses() != null ) {
            Map<String, Object> map = request.getBonuses();
            if ( map != null ) {
                payroll.getBonuses().clear();
                payroll.getBonuses().putAll( map );
            }
            else {
                payroll.setBonuses( null );
            }
        }
        else {
            Map<String, Object> map = request.getBonuses();
            if ( map != null ) {
                payroll.setBonuses( new LinkedHashMap<String, Object>( map ) );
            }
        }
        if ( payroll.getDeductions() != null ) {
            Map<String, Object> map1 = request.getDeductions();
            if ( map1 != null ) {
                payroll.getDeductions().clear();
                payroll.getDeductions().putAll( map1 );
            }
            else {
                payroll.setDeductions( null );
            }
        }
        else {
            Map<String, Object> map1 = request.getDeductions();
            if ( map1 != null ) {
                payroll.setDeductions( new LinkedHashMap<String, Object>( map1 ) );
            }
        }
        payroll.setGrossSalary( request.getGrossSalary() );
        payroll.setNetSalary( request.getNetSalary() );
        payroll.setPayDate( request.getPayDate() );
        payroll.setPayrollPeriod( request.getPayrollPeriod() );
        payroll.setStatus( request.getStatus() );
    }
}
