# HR Management System - Consolidated Architecture

## 🎯 **Project Overview**

---

## 🏗️ **Architecture Overview**

### **3 Consolidated Services:**

#### 1. **HR Core Service** (Port: 8081)
- **Database**: PostgreSQL
- **Modules**: HR Management, Authentication, Time Management, Payroll, Performance
- **Entities**: 36 entities trong 1 database

#### 2. **File Service** (Port: 8082)
- **Database**: MongoDB
- **Functions**: Document management, Image processing, File storage

#### 3. **Notification Service** (Port: 8083)
- **Database**: MongoDB
- **Functions**: Email notifications, External integrations

---

## 📁 **Project Structure**

```
HR-Management-System/
├── README.md                           # Project overview
├── docker-compose.yml                  # Docker deployment
├── consolidated-architecture.md        # Detailed architecture
└── consolidated-diagram.md            # Architecture diagrams
```

---

## 🚀 **Quick Start**

### **Prerequisites:**
- Java 21+
- Maven 3.8+
- Node.js 18+
- PostgreSQL 15+

---

## 📊 **Cost Analysis**

### **Infrastructure Costs (Monthly):**
- **3 Application Servers**: $150
- **1 PostgreSQL Database**: $30
- **2 MongoDB Databases**: $50
- **Redis Cache**: $15
- **Monitoring**: $30
- **Load Balancer**: $30
- **Total**: $305/month

### **Development Costs:**
- **Timeline**: 14 weeks
- **Team Size**: 5-6 people
- **Total Cost**: ~$150,000

### **Cost Savings:**
- **62% infrastructure cost reduction**
- **34% development cost reduction**
- **36% faster time to market**

---

## 🗄️ **Database Schema**

### **PostgreSQL (HR Core Service):**
- **HR Schema**: Companies, Branches, Departments, Positions, Employees, Users
- **Auth Schema**: AuthUsers, Roles, Permissions, UserRoles, Sessions
- **Time Schema**: Attendance, LeaveTypes, LeaveRequests, WorkSchedules
- **Payroll Schema**: SalaryStructures, Payrolls, Bonuses, Deductions
- **Performance Schema**: PerformanceReviews, Goals, Training, Skills

### **MongoDB (File Service):**
- **Collections**: documents, images, reports, templates

### **MongoDB (Notification Service):**
- **Collections**: notifications, email_templates, external_integrations

---

## 🔧 **Technology Stack**

### **Backend:**
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL 15, MongoDB 6
- **Cache**: Redis 7
- **Security**: Spring Security + JWT

### **Infrastructure:**
- **Containerization**: Docker + Docker Compose
- **API Gateway**: Nginx
- **Monitoring**: Prometheus + Grafana

### **Frontend:**
- **Framework**: Next.js 14
- **State Management**: Redux Toolkit
- **UI Library**: Tailwind CSS

---

## 📈 **Implementation Timeline**

### **Phase 1: HR Core Service (10 weeks)**
- Week 1-2: HR Module
- Week 3-4: Auth Module
- Week 5-6: Time Module
- Week 7-8: Payroll Module
- Week 9-10: Performance Module

### **Phase 2: Supporting Services (4 weeks)**
- Week 11-12: File Service
- Week 13-14: Notification Service

**Total Timeline: 14 weeks**

---

## 🔗 **API Endpoints**

### **HR Core Service (Port: 8081):**
```
# HR Management
GET    /api/v1/hr/companies
POST   /api/v1/hr/companies
GET    /api/v1/hr/employees
POST   /api/v1/hr/employees

# Authentication
POST   /api/v1/auth/login
GET    /api/v1/auth/me
GET    /api/v1/auth/roles

# Time Management
GET    /api/v1/time/attendance
POST   /api/v1/time/attendance/check-in
GET    /api/v1/time/leave-requests
POST   /api/v1/time/leave-requests

# Payroll
GET    /api/v1/payroll/salary-structures
POST   /api/v1/payroll/payrolls
GET    /api/v1/payroll/employee/{employeeId}

# Performance
GET    /api/v1/performance/reviews
POST   /api/v1/performance/reviews
GET    /api/v1/performance/goals
```

### **File Service (Port: 8082):**
```
POST   /api/v1/files/upload
GET    /api/v1/files/{id}
DELETE /api/v1/files/{id}
GET    /api/v1/files/download/{id}
```

### **Notification Service (Port: 8083):**
```
POST   /api/v1/notifications/send
GET    /api/v1/notifications/user/{userId}
GET    /api/v1/email-templates
```

---

## 🎯 **Key Benefits**

### **Cost Optimization:**
- **62% infrastructure cost reduction**
- **34% development cost reduction**
- **$5,880 annual savings**

### **Operational Benefits:**
- **Simpler deployment** - 3 services
- **Easier monitoring** - 3 services to monitor
- **Faster development** - 14 weeks timeline
- **Reduced complexity** - fewer integration points

### **Technical Benefits:**
- **Shared components** - common utilities
- **Unified logging** - centralized logging
- **Single database** - for core HR functions
- **Faster debugging** - fewer network calls

---

## 🤝 **Contributing**

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

---

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📞 **Contact**

- **Project Lead**: [Your Name]
- **Email**: [your.email@example.com]
- **Project Link**: [https://github.com/yourusername/hr-management-system](https://github.com/yourusername/hr-management-system)

---

## 🎯 **Roadmap**

### **Phase 1 (Current)**: Core HR Functions
- ✅ Employee management
- ✅ Time tracking
- ✅ Payroll management
- ✅ Performance management

### **Phase 2 (Future)**: Advanced Features
- 🔄 Advanced analytics
- 🔄 Mobile app
- 🔄 API integrations

### **Phase 3 (Future)**: Scalability
- 🔄 Microservices migration (if needed)
- 🔄 Cloud deployment
- 🔄 Advanced monitoring
- 🔄 Auto-scaling
