# REST API Documentation - SmartCare Clinic Management System

## Base URL
```
http://localhost:8080/api
```

## Authentication
All endpoints except `/auth/*` require JWT token in the `Authorization` header:
```
Authorization: Bearer <jwt_token>
```

---

## Authentication Endpoints

### 1. Login
**POST** `/auth/login`

Request:
```json
{
  "email": "doctor@clinic.com",
  "password": "password123"
}
```

Response (200):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "doctor@clinic.com",
    "full_name": "Dr. John Doe",
    "role": "DOCTOR"
  }
}
```

---

## Doctor Endpoints

### 2. Get All Doctors
**GET** `/doctors`

Query Parameters:
- `specialization` (optional): Filter by specialization
- `page` (optional): Page number (default: 1)
- `limit` (optional): Records per page (default: 10)

Response (200):
```json
{
  "data": [
    {
      "id": 1,
      "full_name": "Dr. John Doe",
      "email": "john@clinic.com",
      "specialization": "Cardiology",
      "experience_years": 10,
      "consultation_fee": 500,
      "availability_status": "AVAILABLE"
    }
  ],
  "total": 50,
  "page": 1,
  "limit": 10
}
```

### 3. Get Doctor by ID
**GET** `/doctors/{id}`

Response (200):
```json
{
  "id": 1,
  "full_name": "Dr. John Doe",
  "email": "john@clinic.com",
  "phone": "9876543210",
  "specialization": "Cardiology",
  "license_number": "LIC123456",
  "experience_years": 10,
  "qualification": "MD, DM Cardiology",
  "consultation_fee": 500
}
```

### 4. Create Doctor (Admin Only)
**POST** `/doctors`

Request:
```json
{
  "full_name": "Dr. Jane Smith",
  "email": "jane@clinic.com",
  "phone": "9876543211",
  "specialization": "Neurology",
  "license_number": "LIC123457",
  "experience_years": 8,
  "qualification": "MD, DM Neurology",
  "consultation_fee": 600
}
```

Response (201):
```json
{
  "id": 2,
  "full_name": "Dr. Jane Smith",
  "email": "jane@clinic.com",
  "specialization": "Neurology",
  "message": "Doctor created successfully"
}
```

### 5. Update Doctor (Doctor or Admin)
**PUT** `/doctors/{id}`

Request:
```json
{
  "phone": "9876543212",
  "consultation_fee": 550
}
```

Response (200):
```json
{
  "id": 1,
  "message": "Doctor updated successfully"
}
```

---

## Patient Endpoints

### 6. Register Patient
**POST** `/patients/register`

Request:
```json
{
  "full_name": "John Patient",
  "email": "patient@clinic.com",
  "password": "password123",
  "phone": "9876543213",
  "date_of_birth": "1990-05-15",
  "gender": "M",
  "blood_group": "O+",
  "emergency_contact": "Jane Patient",
  "emergency_phone": "9876543214"
}
```

Response (201):
```json
{
  "id": 1,
  "full_name": "John Patient",
  "email": "patient@clinic.com",
  "message": "Patient registered successfully"
}
```

### 7. Get Patient Profile
**GET** `/patients/me`

Response (200):
```json
{
  "id": 1,
  "full_name": "John Patient",
  "email": "patient@clinic.com",
  "phone": "9876543213",
  "date_of_birth": "1990-05-15",
  "gender": "M",
  "blood_group": "O+",
  "allergies": "Penicillin",
  "address": "123 Main Street",
  "city": "Mumbai",
  "state": "Maharashtra"
}
```

### 8. Update Patient Profile
**PUT** `/patients/me`

Request:
```json
{
  "phone": "9876543215",
  "allergies": "Penicillin, Sulfa",
  "address": "456 Oak Avenue"
}
```

Response (200):
```json
{
  "id": 1,
  "message": "Patient profile updated successfully"
}
```

---

## Appointment Endpoints

### 9. Get Available Slots
**GET** `/appointments/available-slots`

Query Parameters:
- `doctor_id` (required): Doctor ID
- `date` (required): Date in YYYY-MM-DD format

Response (200):
```json
{
  "doctor_id": 1,
  "date": "2024-01-20",
  "available_slots": [
    {
      "start_time": "09:00",
      "end_time": "09:30"
    },
    {
      "start_time": "10:00",
      "end_time": "10:30"
    }
  ]
}
```

### 10. Create Appointment
**POST** `/appointments`

Request:
```json
{
  "doctor_id": 1,
  "appointment_date": "2024-01-20",
  "start_time": "09:00",
  "end_time": "09:30",
  "reason_for_visit": "Regular checkup"
}
```

Response (201):
```json
{
  "id": 1,
  "doctor_id": 1,
  "patient_id": 1,
  "appointment_date": "2024-01-20",
  "start_time": "09:00",
  "status": "SCHEDULED",
  "message": "Appointment booked successfully"
}
```

### 11. Get My Appointments (Patient)
**GET** `/appointments/my-appointments`

Query Parameters:
- `status` (optional): Filter by status (SCHEDULED, COMPLETED, etc.)
- `date_from` (optional): Start date
- `date_to` (optional): End date

Response (200):
```json
{
  "data": [
    {
      "id": 1,
      "doctor_id": 1,
      "doctor_name": "Dr. John Doe",
      "appointment_date": "2024-01-20",
      "start_time": "09:00",
      "end_time": "09:30",
      "reason_for_visit": "Regular checkup",
      "status": "SCHEDULED"
    }
  ],
  "total": 1
}
```

### 12. Get Doctor's Appointments
**GET** `/appointments/doctor-appointments`

Response (200):
```json
{
  "data": [
    {
      "id": 1,
      "patient_id": 1,
      "patient_name": "John Patient",
      "appointment_date": "2024-01-20",
      "start_time": "09:00",
      "status": "SCHEDULED",
      "reason_for_visit": "Regular checkup"
    }
  ],
  "total": 5
}
```

### 13. Update Appointment Status
**PUT** `/appointments/{id}/status`

Request:
```json
{
  "status": "COMPLETED",
  "notes": "Patient doing well"
}
```

Response (200):
```json
{
  "id": 1,
  "status": "COMPLETED",
  "message": "Appointment updated successfully"
}
```

### 14. Cancel Appointment
**DELETE** `/appointments/{id}`

Response (200):
```json
{
  "id": 1,
  "message": "Appointment cancelled successfully"
}
```

---

## Medical History Endpoints

### 15. Get Patient Medical History
**GET** `/medical-history/patient/{patientId}`

Response (200):
```json
{
  "data": [
    {
      "id": 1,
      "appointment_id": 1,
      "doctor_name": "Dr. John Doe",
      "appointment_date": "2024-01-20",
      "diagnosis": "Hypertension",
      "treatment": "Lifestyle changes and medication",
      "medications": "Amlodipine 5mg",
      "follow_up_date": "2024-02-20"
    }
  ],
  "total": 1
}
```

### 16. Create Medical History
**POST** `/medical-history`

Request:
```json
{
  "appointment_id": 1,
  "diagnosis": "Hypertension",
  "treatment": "Lifestyle changes and medication",
  "medications": "Amlodipine 5mg",
  "notes": "Patient to follow low-sodium diet",
  "follow_up_required": true,
  "follow_up_date": "2024-02-20"
}
```

Response (201):
```json
{
  "id": 1,
  "message": "Medical history recorded successfully"
}
```

---

## Prescription Endpoints

### 17. Get Prescriptions for Appointment
**GET** `/prescriptions/appointment/{appointmentId}`

Response (200):
```json
{
  "data": [
    {
      "id": 1,
      "medicine_name": "Amlodipine",
      "dosage": "5mg",
      "frequency": "Once daily",
      "duration": 30,
      "duration_unit": "DAYS",
      "instructions": "Take in morning with water"
    }
  ]
}
```

### 18. Create Prescription
**POST** `/prescriptions`

Request:
```json
{
  "appointment_id": 1,
  "medicines": [
    {
      "medicine_name": "Amlodipine",
      "dosage": "5mg",
      "frequency": "Once daily",
      "duration": 30,
      "duration_unit": "DAYS",
      "instructions": "Take in morning"
    }
  ]
}
```

Response (201):
```json
{
  "id": 1,
  "message": "Prescription created successfully"
}
```

---

## Error Responses

### 400 Bad Request
```json
{
  "error": "Validation failed",
  "details": [
    "Email is invalid",
    "Password must be at least 8 characters"
  ]
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### 403 Forbidden
```json
{
  "error": "Forbidden",
  "message": "You don't have permission to access this resource"
}
```

### 404 Not Found
```json
{
  "error": "Not found",
  "message": "Doctor with ID 999 not found"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal server error",
  "message": "An unexpected error occurred"
}
```

---

## Rate Limiting
- 100 requests per minute per IP address
- Headers returned:
  - `X-RateLimit-Limit: 100`
  - `X-RateLimit-Remaining: 95`
  - `X-RateLimit-Reset: 1705776300`
