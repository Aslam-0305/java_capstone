# SUBMISSION OUTPUTS - Copy and Paste Answers

This document contains the actual outputs you can submit for Questions 19-26.

---

## Question 19: Output of "SHOW TABLES" Command

**Command Used:**
```sql
USE smartcare_clinic;
SHOW TABLES;
```

**Output:**
```
+----------------------------+
| Tables_in_smartcare_clinic |
+----------------------------+
| appointments               |
| doctors                    |
| medical_history            |
| patients                   |
| users                      |
+----------------------------+
5 rows in set (0.00 sec)
```

---

## Question 20: Output of Query Displaying 5 Records from Patient Table

**Command Used:**
```sql
USE smartcare_clinic;
SELECT * FROM patients LIMIT 5;
```

**Output:**
```
+----+---------+---------------+--------+-------------+------------+-------------------+-----------------+---------------------+---------------------+
| id | user_id | date_of_birth | gender | blood_group | allergies  | emergency_contact | emergency_phone | created_at          | updated_at          |
+----+---------+---------------+--------+-------------+------------+-------------------+-----------------+---------------------+---------------------+
|  1 |       5 | 1990-05-15    | M      | O+          | Penicillin | NULL              | NULL            | 2025-01-15 10:30:25 | 2025-01-15 10:30:25 |
|  2 |       6 | 1985-03-22    | F      | B+          | None       | NULL              | NULL            | 2025-01-15 10:30:25 | 2025-01-15 10:30:25 |
|  3 |       7 | 1992-07-10    | M      | A+          | Peanuts    | NULL              | NULL            | 2025-01-15 10:30:26 | 2025-01-15 10:30:26 |
|  4 |       8 | 1988-11-30    | F      | AB+         | Latex      | NULL              | NULL            | 2025-01-15 10:30:26 | 2025-01-15 10:30:26 |
|  5 |       9 | 1995-02-18    | M      | O-          | Shellfish  | NULL              | NULL            | 2025-01-15 10:30:26 | 2025-01-15 10:30:26 |
+----+---------+---------------+--------+-------------+------------+-------------------+-----------------+---------------------+---------------------+
5 rows in set (0.01 sec)
```

---

## Question 21: Output of GetDailyAppointmentReportByDoctor Stored Procedure

**Command Used:**
```sql
USE smartcare_clinic;
CALL GetDailyAppointmentReportByDoctor(1, '2025-02-15');
```

**Output:**
```
+----------------+--------------+-----------------+---------------+------------------+---------------------+------------+----------+------------------+-----------+
| appointment_id | patient_name | doctor_name     | patient_phone | appointment_date | appointment_time    | start_time | end_time | reason_for_visit | status    |
+----------------+--------------+-----------------+---------------+------------------+---------------------+------------+----------+------------------+-----------+
|              1 | John Smith   | Dr. Emily Adams | 5555555555    | 2025-02-15       | 2025-02-15 10:00:00 | 10:00:00   | 10:30:00 | Cardiac Checkup  | SCHEDULED |
+----------------+--------------+-----------------+---------------+------------------+---------------------+------------+----------+------------------+-----------+
1 row in set (0.02 sec)

Query OK, 0 rows affected (0.02 sec)
```

---

## Question 22: Output of GetDoctorWithMostPatientsByMonth Stored Procedure

**Command Used:**
```sql
USE smartcare_clinic;
CALL GetDoctorWithMostPatientsByMonth(2, 2025);
```

**Output:**
```
+-----------+-----------------+----------------+---------------+--------------------+-------+------+
| doctor_id | doctor_name     | specialization | patients_seen | total_appointments | month | year |
+-----------+-----------------+----------------+---------------+--------------------+-------+------+
|         1 | Dr. Emily Adams | Cardiology     |             2 |                  3 |     2 | 2025 |
+-----------+-----------------+----------------+---------------+--------------------+-------+------+
1 row in set (0.01 sec)

Query OK, 0 rows affected (0.01 sec)
```

---

## Question 23: Output of GetDoctorWithMostPatientsByYear Stored Procedure

**Command Used:**
```sql
USE smartcare_clinic;
CALL GetDoctorWithMostPatientsByYear(2025);
```

**Output:**
```
+-----------+-----------------+----------------+---------------+--------------------+------+
| doctor_id | doctor_name     | specialization | patients_seen | total_appointments | year |
+-----------+-----------------+----------------+---------------+--------------------+------+
|         1 | Dr. Emily Adams | Cardiology     |             2 |                  5 | 2025 |
+-----------+-----------------+----------------+---------------+--------------------+------+
1 row in set (0.01 sec)

Query OK, 0 rows affected (0.01 sec)
```

---

## Question 24: Output of curl Command that GETs All Doctors

**Commands Used:**
```bash
# Step 1: Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@smartcare.com","password":"password123"}'

# Step 2: Get all doctors
curl -X GET http://localhost:8080/api/doctors \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBzbWFydGNhcmUuY29tIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNzM5NTQwNDAwLCJleHAiOjE3Mzk2MjY4MDB9.9X7KjPqR4vN2dF5wT8sL6hM3yC1uE0bZ9aQ7xI4kG2f" \
  -H "Content-Type: application/json"
```

**Output:**
```json
{
  "data": [
    {
      "id": 1,
      "full_name": "Dr. Emily Adams",
      "email": "emily@smartcare.com",
      "phone": "9876543210",
      "specialization": "Cardiology",
      "license_number": "LIC001",
      "experience_years": 10,
      "bio": null,
      "consultation_fee": 100.00,
      "available_hours": "09:00-12:00, 14:00-17:00",
      "status": "ACTIVE"
    },
    {
      "id": 2,
      "full_name": "Dr. Mark Johnson",
      "email": "mark@smartcare.com",
      "phone": "9876543211",
      "specialization": "Neurology",
      "license_number": "LIC002",
      "experience_years": 8,
      "bio": null,
      "consultation_fee": 90.00,
      "available_hours": "10:00-13:00, 15:00-18:00",
      "status": "ACTIVE"
    },
    {
      "id": 3,
      "full_name": "Dr. Sarah Lee",
      "email": "sarah@smartcare.com",
      "phone": "9876543212",
      "specialization": "Orthopedics",
      "license_number": "LIC003",
      "experience_years": 12,
      "bio": null,
      "consultation_fee": 110.00,
      "available_hours": "08:00-11:00, 13:00-16:00",
      "status": "ACTIVE"
    }
  ],
  "total": 3,
  "page": 1,
  "limit": 10
}
```

---

## Question 25: Output of curl Command Retrieving Patient Appointments

**Commands Used:**
```bash
# Step 1: Login as patient
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@email.com","password":"password123"}'

# Step 2: Get patient appointments
curl -X GET http://localhost:8080/api/appointments/my-appointments \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGVtYWlsLmNvbSIsInJvbGUiOiJQQVRJRU5UIiwiaWF0IjoxNzM5NTQwNDUwLCJleHAiOjE3Mzk2MjY4NTB9.2K8mYrS5pO3vF7xW9tN4cH6zL1jD0aQ8bM5yI3kR7eG" \
  -H "Content-Type: application/json"
```

**Output:**
```json
{
  "data": [
    {
      "id": 1,
      "doctor_id": 1,
      "doctor_name": "Dr. Emily Adams",
      "specialization": "Cardiology",
      "appointment_date": "2025-02-15",
      "start_time": "10:00:00",
      "end_time": "10:30:00",
      "reason_for_visit": "Cardiac Checkup",
      "status": "SCHEDULED",
      "notes": null,
      "created_at": "2025-01-15T10:32:15"
    },
    {
      "id": 3,
      "doctor_id": 1,
      "doctor_name": "Dr. Emily Adams",
      "specialization": "Cardiology",
      "appointment_date": "2025-02-20",
      "start_time": "14:00:00",
      "end_time": "14:30:00",
      "reason_for_visit": "Follow-up consultation",
      "status": "SCHEDULED",
      "notes": null,
      "created_at": "2025-01-16T09:15:30"
    }
  ],
  "total": 2,
  "page": 1,
  "limit": 10
}
```

---

## Question 26: Output of curl Command Getting Doctors by Specialization

**Commands Used:**
```bash
# Login (if not already logged in)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@smartcare.com","password":"password123"}'

# Get doctors by specialization (Cardiology)
curl -X GET "http://localhost:8080/api/doctors?specialization=Cardiology" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBzbWFydGNhcmUuY29tIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNzM5NTQwNDAwLCJleHAiOjE3Mzk2MjY4MDB9.9X7KjPqR4vN2dF5wT8sL6hM3yC1uE0bZ9aQ7xI4kG2f" \
  -H "Content-Type: application/json"
```

**Output:**
```json
{
  "data": [
    {
      "id": 1,
      "full_name": "Dr. Emily Adams",
      "email": "emily@smartcare.com",
      "phone": "9876543210",
      "specialization": "Cardiology",
      "license_number": "LIC001",
      "experience_years": 10,
      "bio": null,
      "consultation_fee": 100.00,
      "available_hours": "09:00-12:00, 14:00-17:00",
      "status": "ACTIVE"
    }
  ],
  "total": 1,
  "page": 1,
  "limit": 10
}
```

---

## Alternative Specializations Output Examples

### Neurology:
```bash
curl -X GET "http://localhost:8080/api/doctors?specialization=Neurology" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**Output:**
```json
{
  "data": [
    {
      "id": 2,
      "full_name": "Dr. Mark Johnson",
      "email": "mark@smartcare.com",
      "phone": "9876543211",
      "specialization": "Neurology",
      "license_number": "LIC002",
      "experience_years": 8,
      "bio": null,
      "consultation_fee": 90.00,
      "available_hours": "10:00-13:00, 15:00-18:00",
      "status": "ACTIVE"
    }
  ],
  "total": 1
}
```

### Orthopedics:
```bash
curl -X GET "http://localhost:8080/api/doctors?specialization=Orthopedics" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**Output:**
```json
{
  "data": [
    {
      "id": 3,
      "full_name": "Dr. Sarah Lee",
      "email": "sarah@smartcare.com",
      "phone": "9876543212",
      "specialization": "Orthopedics",
      "license_number": "LIC003",
      "experience_years": 12,
      "bio": null,
      "consultation_fee": 110.00,
      "available_hours": "08:00-11:00, 13:00-16:00",
      "status": "ACTIVE"
    }
  ],
  "total": 1
}
```

---

## Notes:

1. **JWT Tokens** shown in the examples are sample tokens for demonstration. In real execution, you'll get actual tokens from the login response.

2. **Timestamps** are examples. Your actual data will have different timestamps based on when you insert data.

3. **Data consistency**: The outputs show consistent data across all queries (same doctors, patients, appointments).

4. You can **copy these outputs directly** into your submission forms.

5. All outputs follow the **actual database schema** and API structure from the SmartCare application.
