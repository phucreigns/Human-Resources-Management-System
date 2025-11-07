package com.phuc.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    Long companyId;

    @Column(name = "company_name", length = 255, nullable = false)
    String companyName;

    @Column(name = "company_code", length = 50, unique = true)
    String companyCode;

    @Column(name = "address", length = 500)
    String address;

    @Column(name = "phone", length = 30)
    String phone;

    @Column(name = "email", length = 255)
    String email;

    @Column(name = "website", length = 255)
    String website;

    @Column(name = "tax_code", length = 50)
    String taxCode;
}


