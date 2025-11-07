-- HR Management System - Complete Database Schema
-- 6 Services, 14 Entities (10 PostgreSQL + 4 MongoDB references)
-- Generated: 2024

-- ============================================================================
-- SERVICE 1: EMPLOYEE SERVICE (PostgreSQL)
-- Database: employee_service_db
-- ============================================================================

-- Entity 1: companies
CREATE TABLE companies (
    company_id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    company_code VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20),
    email VARCHAR(255),
    website VARCHAR(255),
    tax_code VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_companies_code ON companies(company_code);
CREATE INDEX idx_companies_name ON companies(company_name);

-- Entity 2: departments
CREATE TABLE departments (
    department_id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL,
    parent_department_id BIGINT,
    department_name VARCHAR(255) NOT NULL,
    manager_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_company FOREIGN KEY (company_id) 
        REFERENCES companies(company_id) ON DELETE CASCADE,
    CONSTRAINT fk_parent_dept FOREIGN KEY (parent_department_id) 
        REFERENCES departments(department_id) ON DELETE SET NULL,
    CONSTRAINT fk_manager FOREIGN KEY (manager_id) 
        REFERENCES employees(employee_id) ON DELETE SET NULL
);

CREATE INDEX idx_departments_company ON departments(company_id);
CREATE INDEX idx_departments_parent ON departments(parent_department_id);
CREATE INDEX idx_departments_manager ON departments(manager_id);

-- Entity 3: positions
CREATE TABLE positions (
    position_id BIGSERIAL PRIMARY KEY,
    department_id BIGINT NOT NULL,
    position_name VARCHAR(255) NOT NULL,
    position_code VARCHAR(50) NOT NULL,
    salary_min DECIMAL(15, 2),
    salary_max DECIMAL(15, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_department FOREIGN KEY (department_id) 
        REFERENCES departments(department_id) ON DELETE CASCADE
);

CREATE INDEX idx_positions_department ON positions(department_id);
CREATE INDEX idx_positions_code ON positions(position_code);

-- Entity 4: employees
CREATE TABLE employees (
    employee_id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL,
    department_id BIGINT,
    position_id BIGINT,
    manager_id BIGINT,
    employee_code VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    hire_date DATE,
    birth_date DATE,
    gender VARCHAR(20),
    address VARCHAR(500),
    avatar_url VARCHAR(500),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_company FOREIGN KEY (company_id) 
        REFERENCES companies(company_id) ON DELETE CASCADE,
    CONSTRAINT fk_department FOREIGN KEY (department_id) 
        REFERENCES departments(department_id) ON DELETE SET NULL,
    CONSTRAINT fk_position FOREIGN KEY (position_id) 
        REFERENCES positions(position_id) ON DELETE SET NULL,
    CONSTRAINT fk_manager FOREIGN KEY (manager_id) 
        REFERENCES employees(employee_id) ON DELETE SET NULL
);

CREATE INDEX idx_employees_company ON employees(company_id);
CREATE INDEX idx_employees_department ON employees(department_id);
CREATE INDEX idx_employees_position ON employees(position_id);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_employees_code ON employees(employee_code);
CREATE INDEX idx_employees_status ON employees(status);

-- ============================================================================
-- SERVICE 2: TIMETRACKING SERVICE (PostgreSQL)
-- Database: timetracking_service_db
-- ============================================================================

-- Entity 5: attendance_records
CREATE TABLE attendance_records (
    attendance_id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    hours_worked DECIMAL(5, 2),
    status VARCHAR(50) DEFAULT 'ON_TIME',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_employee_date UNIQUE (employee_id, attendance_date)
);

CREATE INDEX idx_attendance_employee ON attendance_records(employee_id);
CREATE INDEX idx_attendance_date ON attendance_records(attendance_date);
CREATE INDEX idx_attendance_status ON attendance_records(status);

-- Entity 6: leave_types
CREATE TABLE leave_types (
    leave_type_id BIGSERIAL PRIMARY KEY,
    leave_type_name VARCHAR(255) NOT NULL,
    leave_type_code VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    max_days_per_year DECIMAL(5, 2),
    requires_approval BOOLEAN DEFAULT TRUE,
    is_paid BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_leave_types_code ON leave_types(leave_type_code);

-- Entity 7: leave_requests
CREATE TABLE leave_requests (
    leave_request_id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    leave_type_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_days DECIMAL(5, 2) NOT NULL,
    reason TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    approved_by BIGINT,
    approved_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT valid_date_range CHECK (end_date >= start_date)
);

CREATE INDEX idx_leave_employee ON leave_requests(employee_id);
CREATE INDEX idx_leave_type ON leave_requests(leave_type_id);
CREATE INDEX idx_leave_status ON leave_requests(status);
CREATE INDEX idx_leave_dates ON leave_requests(start_date, end_date);

-- ============================================================================
-- SERVICE 3: PAYROLL SERVICE (PostgreSQL)
-- Database: payroll_service_db
-- ============================================================================

-- Entity 8: salary_structures
CREATE TABLE salary_structures (
    salary_structure_id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    base_salary DECIMAL(15, 2) NOT NULL,
    salary_components JSONB,
    total_salary DECIMAL(15, 2) NOT NULL,
    effective_from DATE NOT NULL,
    effective_to DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_salary_employee ON salary_structures(employee_id);
CREATE INDEX idx_salary_active ON salary_structures(is_active);
CREATE INDEX idx_salary_dates ON salary_structures(effective_from, effective_to);

-- Entity 9: payrolls
CREATE TABLE payrolls (
    payroll_id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    payroll_period VARCHAR(10) NOT NULL,
    pay_date DATE NOT NULL,
    gross_salary DECIMAL(15, 2) NOT NULL,
    net_salary DECIMAL(15, 2) NOT NULL,
    deductions JSONB,
    bonuses JSONB,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_employee_period UNIQUE (employee_id, payroll_period)
);

CREATE INDEX idx_payroll_employee ON payrolls(employee_id);
CREATE INDEX idx_payroll_period ON payrolls(payroll_period);
CREATE INDEX idx_payroll_status ON payrolls(status);

-- Entity 10: payroll_adjustments
CREATE TABLE payroll_adjustments (
    adjustment_id BIGSERIAL PRIMARY KEY,
    payroll_id BIGINT,
    employee_id BIGINT NOT NULL,
    adjustment_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description TEXT,
    effective_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_adjustment_payroll ON payroll_adjustments(payroll_id);
CREATE INDEX idx_adjustment_employee ON payroll_adjustments(employee_id);
CREATE INDEX idx_adjustment_type ON payroll_adjustments(adjustment_type);
CREATE INDEX idx_adjustment_date ON payroll_adjustments(effective_date);

-- ============================================================================
-- NOTE: MongoDB Collections are not included in SQL schema
-- See MongoDB schema definitions in separate documentation
-- ============================================================================

-- Collections (MongoDB):
-- 11. documents (file_service_db)
-- 12. images (file_service_db)
-- 13. notifications (notification_service_db)
-- 14. templates (notification_service_db)
