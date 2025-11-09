package com.phuc.hr.repository;

import com.phuc.hr.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyId(Long companyId);
    Optional<Company> findByTaxCode(String taxCode);
    boolean existsByTaxCode(String taxCode);
}


