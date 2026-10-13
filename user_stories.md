# User Stories - SmartCare Clinic Management System

## Admin User Stories

### US-001: Admin Login
**As an** Administrator  
**I want to** login to the system with my credentials  
**So that** I can access the admin dashboard and manage the clinic operations

**Acceptance Criteria:**
- Admin can enter username and password
- System validates credentials against database
- Upon successful login, admin is redirected to admin dashboard
- Upon failed login, an error message is displayed
- Session is maintained with JWT token

### US-002: Add New Doctor
**As an** Administrator  
**I want to** add a new doctor to the system  
**So that** the doctor can manage patient appointments

**Acceptance Criteria:**
- Admin can access "Add Doctor" form from dashboard
- Form contains fields: Full Name, Email, Phone, Specialization, License Number, Experience
- System validates email uniqueness
- System validates license number uniqueness
- Doctor account is created with initial password
- Doctor receives email notification with login credentials
- Doctor is assigned appropriate role and permissions

### US-003: View All Doctors
**As an** Administrator  
**I want to** view all registered doctors  
**So that** I can monitor doctor information and status

**Acceptance Criteria:**
- Admin can access doctor list from sidebar
- List displays: Name, Specialization, License, Experience, Status
- Admin can search doctors by name or specialization
- Admin can view doctor details
- Admin can update doctor information
- Admin can deactivate/suspend doctor account

### US-004: View All Appointments
**As an** Administrator  
**I want to** view all appointments in the system  
**So that** I can monitor clinic operations

**Acceptance Criteria:**
- Admin can access complete appointment list
- List displays: Doctor, Patient, Date, Time, Status
- Admin can filter by date range
- Admin can filter by doctor
- Admin can filter by status
- Admin can view appointment details
- Admin can cancel appointments if needed

---

## Doctor User Stories

### US-005: Doctor Login
**As a** Doctor  
**I want to** login to the system with my credentials  
**So that** I can access my appointments and patient information

**Acceptance Criteria:**
- Doctor can enter username and password
- System validates credentials
- Upon successful login, doctor is redirected to doctor dashboard
- Doctor dashboard displays today's appointments
- Session is maintained with JWT token

### US-006: View My Appointments
**As a** Doctor  
**I want to** view all my scheduled appointments  
**So that** I can manage my schedule effectively

**Acceptance Criteria:**
- Doctor can access "My Appointments" section
- List displays patient name, appointment date, time, and reason for visit
- Doctor can view appointment details
- Doctor can filter appointments by date
- Doctor can mark appointment as completed
- Doctor can add notes to completed appointments

### US-007: View Patient Information
**As a** Doctor  
**I want to** view detailed patient information before appointments  
**So that** I can provide better healthcare services

**Acceptance Criteria:**
- Doctor can access patient information from appointment
- View patient: Name, Contact, Medical History, Allergies
- Doctor can view patient's previous appointments
- Doctor can view patient's medical history records
- Doctor can add prescription records

### US-008: Record Medical History
**As a** Doctor  
**I want to** record medical history for patient appointments  
**So that** we maintain proper medical records

**Acceptance Criteria:**
- Doctor can access medical history recording after appointment
- Form contains: Diagnosis, Treatment, Medications, Notes
- Doctor can save medical history record
- Medical history is linked to appointment
- Doctor can view previously recorded history

---

## Patient User Stories

### US-009: Patient Registration
**As a** New Patient  
**I want to** register to the clinic system  
**So that** I can book appointments and access healthcare services

**Acceptance Criteria:**
- Patient can access registration form from login page
- Form contains: Full Name, Email, Phone, Date of Birth, Gender, Blood Group
- System validates email uniqueness
- Email verification is sent to patient
- Patient creates username and password
- Patient receives confirmation message
- Patient can proceed to login

### US-010: Patient Login
**As a** Patient  
**I want to** login to the system  
**So that** I can access my profile and book appointments

**Acceptance Criteria:**
- Patient can enter username and password
- System validates credentials
- Upon successful login, patient is redirected to patient dashboard
- Patient dashboard displays quick action buttons
- Session is maintained with JWT token

### US-011: Find Doctor by Specialization
**As a** Patient  
**I want to** search for doctors by specialization  
**So that** I can find appropriate doctors for my health concern

**Acceptance Criteria:**
- Patient can access "Find Doctor" section
- Patient can enter specialization (e.g., Cardiology, Neurology)
- System displays matching doctors with: Name, Specialization, Experience, Consultation Fee
- Patient can view doctor profile
- Patient can see doctor's available time slots
- Patient can proceed to book appointment

### US-012: Book Appointment
**As a** Patient  
**I want to** book an appointment with a doctor  
**So that** I can receive healthcare services

**Acceptance Criteria:**
- Patient can select date and time from available slots
- Patient can enter reason for visit
- System confirms appointment booking
- Patient receives confirmation email with appointment details
- Patient can view appointment in "My Appointments"
- Patient receives reminder notification before appointment

### US-013: View My Appointments
**As a** Patient  
**I want to** view all my booked appointments  
**So that** I can manage my healthcare schedule

**Acceptance Criteria:**
- Patient can access "My Appointments" section
- List displays: Doctor, Date, Time, Reason for Visit, Status
- Patient can see appointment details
- Patient can cancel upcoming appointments
- Patient can reschedule appointments
- System maintains appointment history

### US-014: View Medical History
**As a** Patient  
**I want to** view my medical history  
**So that** I can track my healthcare records

**Acceptance Criteria:**
- Patient can access "Medical History" section
- History displays: Doctor, Appointment Date, Diagnosis, Treatment
- Patient can view detailed records
- Medical history is organized by date
- Patient can download medical reports

---

## Technical User Stories

### US-015: API Integration
**As a** Developer  
**I want to** have well-documented REST APIs  
**So that** I can integrate with other systems

**Acceptance Criteria:**
- All endpoints return consistent JSON responses
- Error responses include appropriate HTTP status codes
- JWT authentication is enforced on protected endpoints
- API documentation is available (Swagger/OpenAPI)

### US-016: Data Security
**As a** System Administrator  
**I want to** ensure patient data is secure  
**So that** we comply with healthcare regulations

**Acceptance Criteria:**
- Passwords are hashed using bcrypt
- JWT tokens expire after 24 hours
- Sensitive data is encrypted
- CORS is properly configured
- SQL injection prevention is implemented

### US-017: Performance
**As a** System Administrator  
**I want to** ensure the system performs well  
**So that** users have a smooth experience

**Acceptance Criteria:**
- Database queries are optimized with indexes
- Response times < 500ms for standard operations
- System can handle 1000+ concurrent users
- API endpoints support pagination for large datasets
