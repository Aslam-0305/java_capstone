# ALL GITHUB LINKS - Final Submission

## ✅ All Links with Correct Structure (app/src/main/java/com/project/back_end/)

---

## Question 3: Doctor.java (Model)
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/models/Doctor.java**

---

## Question 4: Appointment.java (Model)
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/models/Appointment.java**

---

## Question 5: DoctorController.java
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/controllers/DoctorController.java**

**New Feature Added:** 
- Endpoint: `GET /api/doctors/availability/{user}/{doctorId}/{date}/{token}`
- Retrieves doctor's available time slots for a specific date with token validation

---

## Question 6: AppointmentService.java
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/services/AppointmentService.java**

---

## Question 7: PrescriptionController.java
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/controllers/PrescriptionController.java**

---

## Question 8: PatientRepository.java
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/repo/PatientRepository.java**

**New Methods Added:**
- `findByEmail(String email)` - Retrieve patient by email address
- `findByPhone(String phone)` - Retrieve patient by phone number
- `findByEmailOrPhone(String email, String phone)` - Retrieve patient by email OR phone

---

## Question 9: TokenService.java
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/services/TokenService.java**

---

## Question 10: DoctorService.java
**https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/services/DoctorService.java**

**New Methods Added:**
- `getAvailableTimeSlots(Integer doctorId, String date, String token)` - Returns available time slots for a doctor on a specific date
- `validateDoctorLogin(String email, String password)` - Validates doctor login credentials
- `validateToken(String token)` - Helper method for token validation

---

## Question 11: Dockerfile
**https://github.com/Aslam-0305/java_capstone/blob/main/app/Dockerfile**

---

## Question 12: GitHub Actions Workflow (Compile Backend)
**https://github.com/Aslam-0305/java_capstone/blob/main/.github/workflows/compile-backend.yml**

---

## Additional Important Files:

### User Stories
**https://github.com/Aslam-0305/java_capstone/blob/main/user_stories.md**

### Schema Design
**https://github.com/Aslam-0305/java_capstone/blob/main/schema-design.md**

### Database Init Script
**https://github.com/Aslam-0305/java_capstone/blob/main/database/init.sql**

### Stored Procedures (Updated with corrected column names)
**https://github.com/Aslam-0305/java_capstone/blob/main/database/stored_procedures.sql**

### API Documentation
**https://github.com/Aslam-0305/java_capstone/blob/main/API.md**

### README
**https://github.com/Aslam-0305/java_capstone/blob/main/README.md**

---

## What Was Fixed:

### 1. **Package Structure** ✅
- Removed old `com/smartcare` structure
- All files now use `com.project.back_end` package
- Repository structure matches example: `app/src/main/java/com/project/back_end/`

### 2. **PatientRepository.java** ✅
- Added `findByEmail(String email)` method
- Added `findByPhone(String phone)` method
- Added `findByEmailOrPhone(String email, String phone)` method

### 3. **DoctorController.java** ✅
- Added availability endpoint: `/availability/{user}/{doctorId}/{date}/{token}`
- Includes token validation
- Returns available time slots

### 4. **DoctorService.java** ✅
- Added `getAvailableTimeSlots()` method to retrieve doctor's available time slots
- Added `validateDoctorLogin()` method for credential validation
- Added token validation helper method

### 5. **DoctorRepository.java** ✅
- Added `findByEmail(String email)` method to support login validation

### 6. **Stored Procedures** ✅
- Updated column names: `id` → `doctor_id`
- Updated column names: `total_patients` → `patients_seen`
- Added `doctor_name` column to GetDailyAppointmentReportByDoctor
- Added `appointment_time` column (combination of date and time)

### 7. **Database Init** ✅
- Added 5 patient records (was only 2 before)
- Now meets the requirement of showing exactly 5 patient records

---

## All URLs Are Now Accessible! 🎉

All files are in the correct structure and all links should now work properly. The old `com/smartcare` structure has been completely removed, and only the correct `com/project/back_end` structure remains.

---

## Quick Verification:

Test any link by clicking it - they all follow this pattern:
```
https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/{type}/{FileName}.java
```

Where `{type}` is one of:
- `models` - Entity/Model classes (Doctor, Appointment, Patient, etc.)
- `controllers` - REST Controllers (DoctorController, PatientController, etc.)
- `services` - Service layer (DoctorService, AppointmentService, etc.)
- `repo` - Repository interfaces (DoctorRepository, PatientRepository, etc.)
- `dto` - Data Transfer Objects (DoctorDTO, PatientDTO, etc.)
