# SmartCare Clinic - Submission Answers

This document contains all the SQL statements and curl commands needed for your submission.

---

## Question 19: Show Tables Command

**SQL Statement:**
```sql
USE smartcare_clinic;
SHOW TABLES;
```

**Expected Output:**
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

## Question 20: Display 5 Records from Patient Table

**SQL Statement:**
```sql
USE smartcare_clinic;
SELECT * FROM patients LIMIT 5;
```

**Expected Output:**
```
+----+---------+---------------+--------+-------------+-----------+-------------------+-----------------+---------------------+---------------------+
| id | user_id | date_of_birth | gender | blood_group | allergies | emergency_contact | emergency_phone | created_at          | updated_at          |
+----+---------+---------------+--------+-------------+-----------+-------------------+-----------------+---------------------+---------------------+
|  1 |       5 | 1990-05-15    | M      | O+          | Penicillin| NULL              | NULL            | 2024-01-15 10:30:00 | 2024-01-15 10:30:00 |
|  2 |       6 | 1985-03-22    | F      | B+          | None      | NULL              | NULL            | 2024-01-15 10:31:00 | 2024-01-15 10:31:00 |
+----+---------+---------------+--------+-------------+-----------+-------------------+-----------------+---------------------+---------------------+
2 rows in set (0.00 sec)
```

---

## Question 21: Run GetDailyAppointmentReportByDoctor Stored Procedure

**SQL Statement:**
```sql
USE smartcare_clinic;
CALL GetDailyAppointmentReportByDoctor(1, '2025-02-15');
```

**Expected Output:**
```
+----------------+--------------+---------------+------------------+------------+----------+------------------+-----------+
| appointment_id | patient_name | patient_phone | appointment_date | start_time | end_time | reason_for_visit | status    |
+----------------+--------------+---------------+------------------+------------+----------+------------------+-----------+
|              1 | John Smith   | 5555555555    | 2025-02-15       | 10:00:00   | 10:30:00 | Cardiac Checkup  | SCHEDULED |
+----------------+--------------+---------------+------------------+------------+----------+------------------+-----------+
1 row in set (0.01 sec)
```

**Alternative with different date (if above returns no results):**
```sql
-- First check what appointments exist
SELECT doctor_id, appointment_date FROM appointments;

-- Then use an existing doctor_id and date
CALL GetDailyAppointmentReportByDoctor(1, '2025-02-15');
```

---

## Question 22: Run GetDoctorWithMostPatientsByMonth Stored Procedure

**SQL Statement:**
```sql
USE smartcare_clinic;
CALL GetDoctorWithMostPatientsByMonth(2, 2025);
```

**Expected Output:**
```
+----+-----------------+----------------+----------------+--------------------+-------+------+
| id | doctor_name     | specialization | total_patients | total_appointments | month | year |
+----+-----------------+----------------+----------------+--------------------+-------+------+
|  1 | Dr. Emily Adams | Cardiology     |              1 |                  1 |     2 | 2025 |
+----+-----------------+----------------+----------------+--------------------+-------+------+
1 row in set (0.01 sec)
```

**Note:** Replace month (2) and year (2025) with actual data in your database.

---

## Question 23: Run GetDoctorWithMostPatientsByYear Stored Procedure

**SQL Statement:**
```sql
USE smartcare_clinic;
CALL GetDoctorWithMostPatientsByYear(2025);
```

**Expected Output:**
```
+----+-----------------+----------------+----------------+--------------------+------+
| id | doctor_name     | specialization | total_patients | total_appointments | year |
+----+-----------------+----------------+----------------+--------------------+------+
|  1 | Dr. Emily Adams | Cardiology     |              2 |                  3 | 2025 |
+----+-----------------+----------------+----------------+--------------------+------+
1 row in set (0.01 sec)
```

**Note:** Replace year (2025) with actual data in your database.

---

## Question 24: curl Command to GET All Doctors

**Step 1: Login to get JWT token**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"admin@smartcare.com\",\"password\":\"password123\"}"
```

**Step 2: GET all doctors (replace YOUR_JWT_TOKEN with token from step 1)**
```bash
curl -X GET http://localhost:8080/api/doctors \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json"
```

**Expected Output:**
```json
{
  "data": [
    {
      "id": 1,
      "full_name": "Dr. Emily Adams",
      "email": "emily@smartcare.com",
      "specialization": "Cardiology",
      "license_number": "LIC001",
      "experience_years": 10,
      "consultation_fee": 100.00
    },
    {
      "id": 2,
      "full_name": "Dr. Mark Johnson",
      "email": "mark@smartcare.com",
      "specialization": "Neurology",
      "license_number": "LIC002",
      "experience_years": 8,
      "consultation_fee": 90.00
    },
    {
      "id": 3,
      "full_name": "Dr. Sarah Lee",
      "email": "sarah@smartcare.com",
      "specialization": "Orthopedics",
      "license_number": "LIC003",
      "experience_years": 12,
      "consultation_fee": 110.00
    }
  ],
  "total": 3
}
```

---

## Question 25: curl Command to GET Patient Appointments with Patient Credentials

**Step 1: Login as patient to get JWT token**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"john@email.com\",\"password\":\"password123\"}"
```

**Step 2: GET patient's appointments (replace PATIENT_JWT_TOKEN with token from step 1)**
```bash
curl -X GET http://localhost:8080/api/appointments/my-appointments \
  -H "Authorization: Bearer PATIENT_JWT_TOKEN" \
  -H "Content-Type: application/json"
```

**Expected Output:**
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
      "status": "SCHEDULED"
    }
  ],
  "total": 1
}
```

---

## Question 26: curl Command to GET Doctor Details by Speciality

**Step 1: Login to get JWT token (if not already logged in)**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"admin@smartcare.com\",\"password\":\"password123\"}"
```

**Step 2: GET doctors by specialization (example: Cardiology)**
```bash
curl -X GET "http://localhost:8080/api/doctors?specialization=Cardiology" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json"
```

**Expected Output:**
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
      "consultation_fee": 100.00,
      "available_hours": "09:00-12:00, 14:00-17:00"
    }
  ],
  "total": 1
}
```

**Alternative Specializations to Try:**
- `specialization=Neurology`
- `specialization=Orthopedics`

---

## PowerShell Commands (For Windows)

If you're using PowerShell instead of bash, here are the equivalent commands:

### Question 24 - PowerShell:
```powershell
# Login
$loginResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -ContentType "application/json" -Body '{"email":"admin@smartcare.com","password":"password123"}'
$token = $loginResponse.token

# Get all doctors
Invoke-RestMethod -Uri "http://localhost:8080/api/doctors" -Method GET -Headers @{"Authorization"="Bearer $token"; "Content-Type"="application/json"}
```

### Question 25 - PowerShell:
```powershell
# Login as patient
$patientLogin = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -ContentType "application/json" -Body '{"email":"john@email.com","password":"password123"}'
$patientToken = $patientLogin.token

# Get patient appointments
Invoke-RestMethod -Uri "http://localhost:8080/api/appointments/my-appointments" -Method GET -Headers @{"Authorization"="Bearer $patientToken"; "Content-Type"="application/json"}
```

### Question 26 - PowerShell:
```powershell
# Get doctors by specialization
Invoke-RestMethod -Uri "http://localhost:8080/api/doctors?specialization=Cardiology" -Method GET -Headers @{"Authorization"="Bearer $token"; "Content-Type"="application/json"}
```

---

## Important Notes:

1. **Before running these commands**, make sure:
   - MySQL database is running
   - The application is running on `http://localhost:8080`
   - Database has been initialized with `init.sql`
   - Stored procedures have been created with `stored_procedures.sql`

2. **To run the application**:
   ```bash
   # Using Docker
   docker-compose up -d
   
   # OR manually
   mvn spring-boot:run
   ```

3. **To initialize the database**:
   ```bash
   mysql -u root -p < database/init.sql
   mysql -u root -p smartcare_clinic < database/stored_procedures.sql
   ```

4. **Default credentials** in the system:
   - Admin: `admin@smartcare.com` / `password123`
   - Doctor: `emily@smartcare.com` / `password123`
   - Patient: `john@email.com` / `password123`

5. **Password hash** in database: `$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2`
   - This is bcrypt hash for `password123`

---

## How to Capture the Output:

1. **For SQL statements**: Run them in MySQL Workbench or command line and take a screenshot
2. **For curl commands**: 
   - Run in terminal/PowerShell
   - Copy the JSON output
   - Format it nicely (you can use https://jsonformatter.org/)
   - Take a screenshot or copy the formatted text

3. **Save outputs to files**:
   ```bash
   # SQL output
   mysql -u root -p smartcare_clinic -e "SHOW TABLES;" > question19_output.txt
   
   # curl output
   curl ... > question24_output.json
   ```
