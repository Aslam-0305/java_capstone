# Submission Outputs Guide - SmartCare Clinic Management System

This document provides instructions on how to generate all the outputs required for submission questions 13-26.

---

## Prerequisites

Ensure Docker and Docker Compose are installed:
```bash
docker --version
docker-compose --version
```

---

## Getting Started

### Step 1: Start the Application

```bash
# Navigate to project root
cd smartcare-clinic

# Start all services
docker-compose up -d

# Wait 30 seconds for services to be healthy
docker-compose ps
```

### Step 2: Verify Services

```bash
# Check MySQL is running
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic -e "SELECT 1;"

# Check Backend is running
curl http://localhost:8080/actuator/health

# Check Frontend is running
curl http://localhost
```

---

## Questions 19-23: SQL Outputs

### Question 19: SHOW TABLES Output

```bash
# Connect to MySQL and run SHOW TABLES
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic << EOF
show tables;
EOF
```

**Expected Output:**
```
+---------------------------+
| Tables_in_smartcare_clinic |
+---------------------------+
| appointments              |
| doctors                   |
| medical_history           |
| patients                  |
| prescriptions             |
| users                     |
+---------------------------+
```

---

### Question 20: 5 Patient Records

```bash
# Get 5 patient records
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic << EOF
SELECT * FROM patients LIMIT 5;
EOF
```

**Expected Output:**
```
+----+---------+---------------+-----+-------------+------------+----------+
| id | user_id | date_of_birth | sex | blood_group | allergies  | address  |
+----+---------+---------------+-----+-------------+------------+----------+
|  1 |       3 | 1990-05-15    | M   | O+          | Penicillin | 123 Main |
|  2 |       4 | 1985-08-22    | F   | B+          | NULL       | 456 Oak  |
|  3 |       5 | 1992-03-10    | M   | AB+         | Sulfa      | 789 Pine |
|  4 |       6 | 1988-11-28    | F   | A-          | NULL       | 321 Elm  |
|  5 |       7 | 1995-07-03    | M   | O-          | Latex      | 654 Birch|
+----+---------+---------------+-----+-------------+------------+----------+
```

---

### Question 21: GetDailyAppointmentReportByDoctor Output

```bash
# Call the stored procedure
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic << EOF
CALL GetDailyAppointmentReportByDoctor(1, '2024-01-20');
EOF
```

**Expected Output:**
```
+----------------+---------------+--------------+------------------+------------+----------+------------------+--------+
| appointment_id | patient_name  | patient_phone| appointment_date | start_time | end_time | reason_for_visit | status |
+----------------+---------------+--------------+------------------+------------+----------+------------------+--------+
|              1 | John Patient  | 9876543213   | 2024-01-20       | 09:00      | 09:30    | Regular checkup  | SCHEDULED |
|              2 | Jane Doe      | 9876543214   | 2024-01-20       | 10:00      | 10:30    | Blood pressure   | SCHEDULED |
+----------------+---------------+--------------+------------------+------------+----------+------------------+--------+
```

---

### Question 22: GetDoctorWithMostPatientsByMonth Output

```bash
# Call the stored procedure for January 2024
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic << EOF
CALL GetDoctorWithMostPatientsByMonth(1, 2024);
EOF
```

**Expected Output:**
```
+----+-----------------+---------------+----------------+--------------------+-------+------+
| id | doctor_name     | specialization| total_patients | total_appointments | month | year |
+----+-----------------+---------------+----------------+--------------------+-------+------+
|  1 | Dr. John Doe    | Cardiology    |              3 |                  4 |     1 | 2024 |
+----+-----------------+---------------+----------------+--------------------+-------+------+
```

---

### Question 23: GetDoctorWithMostPatientsByYear Output

```bash
# Call the stored procedure for 2024
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic << EOF
CALL GetDoctorWithMostPatientsByYear(2024);
EOF
```

**Expected Output:**
```
+----+-----------------+---------------+----------------+--------------------+------+
| id | doctor_name     | specialization| total_patients | total_appointments | year |
+----+-----------------+---------------+----------------+--------------------+------+
|  1 | Dr. John Doe    | Cardiology    |              5 |                  8 | 2024 |
+----+-----------------+---------------+----------------+--------------------+------+
```

---

## Questions 24-26: curl Command Outputs

### Step 1: Get Authentication Token

```bash
# Login as Doctor
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "doctor@clinic.com",
    "password": "doctor123"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkb2N0b3JAY2xpbmljLmNvbSIsImV4cCI6MTY5NjAzMDQwMH0.xyz...",
  "user": {
    "id": 2,
    "email": "doctor@clinic.com",
    "full_name": "Dr. John Doe",
    "role": "DOCTOR"
  }
}
```

**Store the token for next requests:**
```bash
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...."
```

---

### Question 24: GET All Doctors

```bash
# Get all doctors
curl -X GET http://localhost:8080/api/doctors \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Expected Output:**
```json
{
  "data": [
    {
      "id": 1,
      "full_name": "Dr. John Doe",
      "email": "john@clinic.com",
      "phone": "9876543210",
      "specialization": "Cardiology",
      "license_number": "LIC123456",
      "experience_years": 10,
      "consultation_fee": 500
    },
    {
      "id": 2,
      "full_name": "Dr. Jane Smith",
      "email": "jane@clinic.com",
      "phone": "9876543211",
      "specialization": "Neurology",
      "license_number": "LIC123457",
      "experience_years": 8,
      "consultation_fee": 600
    },
    {
      "id": 3,
      "full_name": "Dr. Mark Wilson",
      "email": "mark@clinic.com",
      "phone": "9876543212",
      "specialization": "Orthopedics",
      "license_number": "LIC123458",
      "experience_years": 12,
      "consultation_fee": 550
    }
  ],
  "total": 3,
  "page": 1,
  "limit": 10
}
```

---

### Question 25: Patient Appointments with Login

```bash
# Step 1: Login as Patient
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "patient@clinic.com",
    "password": "patient123"
  }' > patient_login.json

# Extract patient token
PATIENT_TOKEN=$(grep -o '"token":"[^"]*' patient_login.json | cut -d'"' -f4)

# Step 2: Get patient appointments
curl -X GET http://localhost:8080/api/appointments/my-appointments \
  -H "Authorization: Bearer $PATIENT_TOKEN" \
  -H "Content-Type: application/json"
```

**Expected Output:**
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
    },
    {
      "id": 2,
      "doctor_id": 1,
      "doctor_name": "Dr. John Doe",
      "appointment_date": "2024-01-22",
      "start_time": "14:00",
      "end_time": "14:30",
      "reason_for_visit": "Follow-up",
      "status": "SCHEDULED"
    }
  ],
  "total": 2
}
```

---

### Question 26: GET Doctors by Specialization

```bash
# Get doctors by specialization (Cardiology)
curl -X GET "http://localhost:8080/api/doctors?specialization=Cardiology" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# Get doctors by specialization (Neurology)
curl -X GET "http://localhost:8080/api/doctors?specialization=Neurology" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# Get doctors by specialization (Orthopedics)
curl -X GET "http://localhost:8080/api/doctors?specialization=Orthopedics" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Expected Output:**
```json
{
  "data": [
    {
      "id": 1,
      "full_name": "Dr. John Doe",
      "email": "john@clinic.com",
      "specialization": "Cardiology",
      "experience_years": 10,
      "consultation_fee": 500
    }
  ],
  "total": 1,
  "page": 1,
  "limit": 10
}
```

---

## Questions 13-18: Screenshot Instructions

### Accessing the Application

Open your browser and go to: `http://localhost`

### Question 13: Admin Login Screen

1. Navigate to http://localhost
2. Select "Admin" from the role dropdown
3. Click the login button to see the admin login screen
4. Take a screenshot

**Screenshot should show:**
- Login form with email and password fields
- "Admin Login" heading
- Login button
- Remember me checkbox

---

### Question 14: Doctor Login Screen

1. Navigate to http://localhost
2. Select "Doctor" from the role dropdown
3. Click on "Doctor Login"
4. Take a screenshot of the doctor login screen

**Screenshot should show:**
- Login form with email and password fields
- "Doctor Login" heading
- Login button
- Remember me checkbox

---

### Question 15: Patient Login Screen

1. Navigate to http://localhost
2. Select "Patient" from the role dropdown
3. Click on "Patient Login"
4. Take a screenshot of the patient login screen

**Screenshot should show:**
- Login form with email and password fields
- "Patient Login" heading
- Login button or Register link
- Role indicator

---

### Question 16: Admin Adding Doctor

1. Login as Admin (email: admin@clinic.com, password: admin123)
2. Navigate to "Add Doctor" section
3. Fill in doctor details:
   - Full Name: Dr. Test Doctor
   - Email: testdoctor@clinic.com
   - Phone: 9876543220
   - Specialization: Cardiology
   - License: LIC999999
   - Experience: 5 years
4. Click "Add Doctor"
5. Take a screenshot showing the form and success message

**Screenshot should show:**
- Add Doctor form with all fields
- Filled-in information
- Submit button
- Success notification

---

### Question 17: Patient Finding Doctor

1. Login as Patient (email: patient@clinic.com, password: patient123)
2. Navigate to "Find Doctor" section
3. Enter search criteria (e.g., Specialization: "Cardiology")
4. Click "Search"
5. Take a screenshot showing search results

**Screenshot should show:**
- Search form or filters
- Doctor results with:
  - Doctor name
  - Specialization
  - Experience
  - Consultation fee
  - Book appointment button
- At least 1-2 doctors in results

---

### Question 18: Doctor Viewing Appointments

1. Login as Doctor (email: doctor@clinic.com, password: doctor123)
2. Navigate to "My Appointments" or "Dashboard"
3. Take a screenshot showing the appointment list

**Screenshot should show:**
- List of appointments with:
  - Patient name
  - Appointment date and time
  - Appointment status
  - Reason for visit
- Action buttons (complete, reschedule, cancel)

---

## Bash Script for All SQL Outputs

Save this as `generate_sql_outputs.sh`:

```bash
#!/bin/bash

echo "=== QUESTION 19: SHOW TABLES ==="
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic -e "SHOW TABLES;"

echo -e "\n=== QUESTION 20: 5 PATIENT RECORDS ==="
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic -e "SELECT * FROM patients LIMIT 5;"

echo -e "\n=== QUESTION 21: DAILY APPOINTMENT REPORT ==="
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic -e "CALL GetDailyAppointmentReportByDoctor(1, '2024-01-20');"

echo -e "\n=== QUESTION 22: DOCTOR MOST PATIENTS BY MONTH ==="
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic -e "CALL GetDoctorWithMostPatientsByMonth(1, 2024);"

echo -e "\n=== QUESTION 23: DOCTOR MOST PATIENTS BY YEAR ==="
docker-compose exec mysql mysql -u smartcare_user -p smartcare_clinic -e "CALL GetDoctorWithMostPatientsByYear(2024);"
```

Run it with:
```bash
chmod +x generate_sql_outputs.sh
./generate_sql_outputs.sh
```

---

## Bash Script for All curl Outputs

Save this as `generate_curl_outputs.sh`:

```bash
#!/bin/bash

# Get token
echo "Getting authentication token..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"doctor@clinic.com","password":"doctor123"}')

TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Token: $TOKEN"

echo -e "\n=== QUESTION 24: GET ALL DOCTORS ==="
curl -s -X GET http://localhost:8080/api/doctors \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" | jq .

echo -e "\n=== QUESTION 25: PATIENT APPOINTMENTS ==="
# Login as patient
PATIENT_LOGIN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"patient@clinic.com","password":"patient123"}')

PATIENT_TOKEN=$(echo $PATIENT_LOGIN | grep -o '"token":"[^"]*' | cut -d'"' -f4)

curl -s -X GET http://localhost:8080/api/appointments/my-appointments \
  -H "Authorization: Bearer $PATIENT_TOKEN" \
  -H "Content-Type: application/json" | jq .

echo -e "\n=== QUESTION 26: DOCTORS BY SPECIALIZATION ==="
curl -s -X GET "http://localhost:8080/api/doctors?specialization=Cardiology" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" | jq .
```

Run it with:
```bash
chmod +x generate_curl_outputs.sh
./generate_curl_outputs.sh
```

---

## Troubleshooting

### Services not starting
```bash
# Check logs
docker-compose logs -f

# Rebuild containers
docker-compose down
docker-compose up -d --build
```

### MySQL not responding
```bash
# Check MySQL status
docker-compose exec mysql mysql -u root -p -e "SELECT 1;" -password=root123

# Restart MySQL
docker-compose restart mysql
```

### Backend not responding
```bash
# Check backend logs
docker-compose logs -f backend

# Check health
curl http://localhost:8080/actuator/health
```

### Frontend not loading
```bash
# Check nginx logs
docker-compose logs -f frontend

# Check if frontend is accessible
curl -I http://localhost
```

---

## Next Steps

1. Generate all outputs using the scripts above
2. Take screenshots for questions 13-18
3. Copy-paste all outputs into submission form
4. Double-check all required information is included
5. Submit to grading platform

---

**Ready to submit!** ✅
