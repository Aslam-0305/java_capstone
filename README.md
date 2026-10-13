# SmartCare Clinic Management System

A comprehensive full-stack Java/Spring Boot application for managing clinic operations, doctor schedules, patient appointments, and medical records.

## Project Overview

SmartCare is a modern clinic management system designed to streamline healthcare operations. It provides role-based portals for administrators, doctors, and patients with features including appointment scheduling, medical history tracking, and prescription management.

### Key Features

- **Admin Portal**: Manage doctors, view all appointments, monitor system operations
- **Doctor Portal**: View schedules, manage patient appointments, record medical history
- **Patient Portal**: Search doctors by specialization, book appointments, view medical history
- **JWT Authentication**: Secure token-based authentication
- **RESTful APIs**: Well-documented REST endpoints with Swagger/OpenAPI
- **Multi-database**: MySQL for relational data, MongoDB for flexible documents
- **Docker Support**: Full containerization with docker-compose
- **CI/CD Pipeline**: GitHub Actions for automated build and deployment

## Technology Stack

### Backend
- **Framework**: Spring Boot 2.7.15
- **Language**: Java 11
- **Build Tool**: Maven 3.8.4
- **Security**: JWT (jjwt 0.11.5), bcrypt

### Database
- **Relational**: MySQL 8.0
- **NoSQL**: MongoDB 5.0

### Frontend
- **HTML5**, **CSS3**, **Vanilla JavaScript (ES6+)**
- No build tools required - runs directly in browser

### DevOps
- **Containerization**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
- **Reverse Proxy**: Nginx
- **Code Quality**: SonarQube, Checkstyle, SpotBugs

## Project Structure

```
smartcare-clinic/
├── .github/
│   └── workflows/
│       ├── compile-backend.yml
│       ├── lint.yml
│       └── deploy.yml
├── src/main/java/com/smartcare/
│   ├── SmartcareApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── CorsConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── DoctorController.java
│   │   ├── PatientController.java
│   │   ├── AppointmentController.java
│   │   ├── MedicalHistoryController.java
│   │   └── PrescriptionController.java
│   ├── service/
│   │   ├── TokenService.java
│   │   ├── DoctorService.java
│   │   ├── PatientService.java
│   │   ├── AppointmentService.java
│   │   ├── MedicalHistoryService.java
│   │   └── PrescriptionService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── DoctorRepository.java
│   │   ├── PatientRepository.java
│   │   ├── AppointmentRepository.java
│   │   ├── MedicalHistoryRepository.java
│   │   └── PrescriptionRepository.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Doctor.java
│   │   ├── Patient.java
│   │   ├── Appointment.java
│   │   ├── MedicalHistory.java
│   │   └── Prescription.java
│   └── dto/
│       ├── LoginRequest.java
│       ├── LoginResponse.java
│       ├── DoctorDTO.java
│       └── ...
├── src/main/resources/
│   └── application.yml
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── style.css
│   └── js/
│       ├── api.js
│       ├── auth.js
│       └── main.js
├── database/
│   ├── init.sql
│   └── stored_procedures.sql
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── nginx.conf
├── user_stories.md
├── schema-design.md
├── API.md
└── README.md
```

## Installation & Setup

### Prerequisites
- Docker & Docker Compose (recommended)
- OR
- Java 11+, Maven 3.8.4, MySQL 8.0, MongoDB 5.0

### Quick Start with Docker

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/smartcare-clinic.git
   cd smartcare-clinic
   ```

2. **Build and run with Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Wait for services to be healthy**
   ```bash
   docker-compose ps
   ```

4. **Access the application**
   - Frontend: http://localhost:80
   - Backend API: http://localhost:8080/api
   - API Docs: http://localhost:8080/swagger-ui.html

### Manual Setup (Without Docker)

1. **Create MySQL Database**
   ```bash
   mysql -u root -p < database/init.sql
   mysql -u root -p smartcare_clinic < database/stored_procedures.sql
   ```

2. **Configure Database Connection**
   Edit `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/smartcare_clinic
       username: root
       password: your_password
   ```

3. **Build the Application**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Start Frontend Server**
   ```bash
   # Serve frontend folder with any HTTP server
   python -m http.server 8000 --directory frontend
   # or use npm
   npx http-server frontend
   ```

## Default Credentials

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@clinic.com | admin123 |
| Doctor | doctor@clinic.com | doctor123 |
| Patient | patient@clinic.com | patient123 |

## API Documentation

See [API.md](./API.md) for complete REST API documentation including:
- Authentication endpoints
- Doctor management endpoints
- Patient endpoints
- Appointment management
- Medical history tracking
- Prescription management

### Example API Calls

**Get All Doctors**
```bash
curl -H "Authorization: Bearer <token>" \
  http://localhost:8080/api/doctors?specialization=Cardiology
```

**Book Appointment**
```bash
curl -X POST -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "doctor_id": 1,
    "appointment_date": "2024-01-20",
    "start_time": "09:00",
    "reason_for_visit": "Regular checkup"
  }' \
  http://localhost:8080/api/appointments
```

## Database Schema

The system uses MySQL with the following main entities:
- **Users**: Base entity with role-based inheritance
- **Doctors**: Specialization of Users
- **Patients**: Specialization of Users
- **Appointments**: Link between Doctor and Patient
- **Medical History**: Records for patient visits
- **Prescriptions**: Medication records (also in MongoDB)

See [schema-design.md](./schema-design.md) for detailed ER diagram and table descriptions.

## User Stories

Complete user stories for Admin, Doctor, and Patient roles are documented in [user_stories.md](./user_stories.md).

## Stored Procedures

The system includes 4 key stored procedures:

1. **GetDailyAppointmentReportByDoctor**: Get all appointments for a doctor on a specific date
2. **GetDoctorWithMostPatientsByMonth**: Find the doctor with most patients in a month
3. **GetDoctorWithMostPatientsByYear**: Find the doctor with most patients in a year
4. **GetAppointmentStatistics**: Get appointment statistics for a date range

```sql
CALL GetDailyAppointmentReportByDoctor(1, '2024-01-20');
CALL GetDoctorWithMostPatientsByMonth(1, 2024);
CALL GetDoctorWithMostPatientsByYear(2024);
CALL GetAppointmentStatistics('2024-01-01', '2024-01-31');
```

## CI/CD Pipelines

### GitHub Actions Workflows

1. **compile-backend.yml**: Compiles backend, runs tests, generates coverage reports
2. **lint.yml**: Runs code quality checks (Checkstyle, SpotBugs, PMD)
3. **deploy.yml**: Builds Docker image, pushes to registry, deploys to server

Triggered on:
- Push to main/develop branches
- Pull requests
- Manual trigger via workflow_dispatch

## Security Features

- **Password Hashing**: bcrypt with salt rounds
- **JWT Tokens**: 24-hour expiration
- **CORS**: Configured to allow only specific origins
- **SQL Injection Prevention**: Parameterized queries via JPA
- **Input Validation**: Spring Validation annotations
- **Role-Based Access Control**: @PreAuthorize annotations

## Performance Optimizations

- Database indexes on frequently queried columns
- Lazy loading of entities to reduce memory footprint
- API pagination support for large datasets
- Connection pooling with HikariCP
- Query optimization with proper JOIN strategies

## Monitoring & Logging

- Spring Boot Actuator endpoints at `/actuator`
- Detailed logging to console and file
- Application metrics exposed at `/actuator/metrics`
- Health check endpoint at `/actuator/health`

## Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -am 'Add new feature'`
3. Push to branch: `git push origin feature/your-feature`
4. Create a Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support

For issues, feature requests, or questions:
- GitHub Issues: https://github.com/yourusername/smartcare-clinic/issues
- Email: support@smartcare-clinic.com

## Troubleshooting

### Docker Issues
```bash
# View logs
docker-compose logs -f backend

# Restart services
docker-compose restart

# Full reset
docker-compose down -v
docker-compose up -d
```

### Database Connection Issues
```bash
# Check MySQL status
docker-compose exec mysql mysql -u root -p -e "SELECT 1"

# View MySQL logs
docker-compose logs mysql
```

### Build Failures
```bash
# Clean build
mvn clean install -U

# Skip tests
mvn clean install -DskipTests
```

## Future Enhancements

- [ ] Appointment reminders via SMS/Email
- [ ] Telemedicine integration
- [ ] Advanced prescription management with barcode scanning
- [ ] Analytics dashboard for clinic performance
- [ ] Mobile app for iOS/Android
- [ ] Multi-language support
- [ ] Payment integration
- [ ] Insurance claim management
