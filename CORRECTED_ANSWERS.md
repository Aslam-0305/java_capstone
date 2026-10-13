# CORRECTED SUBMISSION ANSWERS

## Updated Answers Based on Grading Feedback

---

## Question 20: Submit 5 Patient Records ✅ FIXED

**SQL Command:**
```sql
USE smartcare_clinic;
SELECT * FROM patients LIMIT 5;
```

**Corrected Output (Now shows exactly 5 patients):**
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

**What was fixed:**
- Added 3 more patients to database to have exactly 5 records
- Now displays all 5 patient records as required

---

## Question 21: GetDailyAppointmentReportByDoctor ✅ FIXED

**SQL Command:**
```sql
USE smartcare_clinic;
CALL GetDailyAppointmentReportByDoctor(1, '2025-02-15');
```

**Corrected Output (Now includes doctor_name and appointment_time):**
```
+----------------+--------------+-----------------+---------------+------------------+---------------------+------------+----------+------------------+-----------+
| appointment_id | patient_name | doctor_name     | patient_phone | appointment_date | appointment_time    | start_time | end_time | reason_for_visit | status    |
+----------------+--------------+-----------------+---------------+------------------+---------------------+------------+----------+------------------+-----------+
|              1 | John Smith   | Dr. Emily Adams | 5555555555    | 2025-02-15       | 2025-02-15 10:00:00 | 10:00:00   | 10:30:00 | Cardiac Checkup  | SCHEDULED |
+----------------+--------------+-----------------+---------------+------------------+---------------------+------------+----------+------------------+-----------+
1 row in set (0.02 sec)
```

**What was fixed:**
- Added `doctor_name` column to the output
- Added `appointment_time` column (combines date and time)
- Modified stored procedure to join with doctor's user table to get doctor name

---

## Question 22: GetDoctorWithMostPatientsByMonth ✅ FIXED

**SQL Command:**
```sql
USE smartcare_clinic;
CALL GetDoctorWithMostPatientsByMonth(2, 2025);
```

**Corrected Output (Now uses doctor_id and patients_seen):**
```
+-----------+-----------------+----------------+---------------+--------------------+-------+------+
| doctor_id | doctor_name     | specialization | patients_seen | total_appointments | month | year |
+-----------+-----------------+----------------+---------------+--------------------+-------+------+
|         1 | Dr. Emily Adams | Cardiology     |             2 |                  3 |     2 | 2025 |
+-----------+-----------------+----------------+---------------+--------------------+-------+------+
1 row in set (0.01 sec)
```

**What was fixed:**
- Changed column name from `id` to `doctor_id`
- Changed column name from `total_patients` to `patients_seen`
- Output now matches the required format

---

## Question 23: GetDoctorWithMostPatientsByYear ✅ FIXED

**SQL Command:**
```sql
USE smartcare_clinic;
CALL GetDoctorWithMostPatientsByYear(2025);
```

**Corrected Output (Now uses doctor_id and patients_seen):**
```
+-----------+-----------------+----------------+---------------+--------------------+------+
| doctor_id | doctor_name     | specialization | patients_seen | total_appointments | year |
+-----------+-----------------+----------------+---------------+--------------------+------+
|         1 | Dr. Emily Adams | Cardiology     |             2 |                  5 | 2025 |
+-----------+-----------------+----------------+---------------+--------------------+------+
1 row in set (0.01 sec)
```

**What was fixed:**
- Changed column name from `id` to `doctor_id`
- Changed column name from `total_patients` to `patients_seen`
- Output now matches the required format

---

## Summary of Changes Made:

### 1. Database Changes (database/init.sql):
- Added 3 more patient records (Robert Brown, Susan Wilson, Michael Davis)
- Now has 5 patients total in the database

### 2. Stored Procedure Changes (database/stored_procedures.sql):

**GetDailyAppointmentReportByDoctor:**
- Added JOIN with doctors and users table to get doctor name
- Added `doctor_name` column to SELECT
- Added `appointment_time` column (CONCAT of date and time)

**GetDoctorWithMostPatientsByMonth:**
- Changed `d.id` to `d.id AS doctor_id`
- Changed `total_patients` to `patients_seen`
- Updated ORDER BY clause to use new column name

**GetDoctorWithMostPatientsByYear:**
- Changed `d.id` to `d.id AS doctor_id`
- Changed `total_patients` to `patients_seen`
- Updated ORDER BY clause to use new column name

---

## All GitHub Links (With Correct Structure):

1. **PatientRepository.java:**
   https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/repo/PatientRepository.java

2. **DoctorService.java:**
   https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/services/DoctorService.java

3. **DoctorController.java:**
   https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/controllers/DoctorController.java

4. **Doctor.java (Model):**
   https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/models/Doctor.java

5. **Appointment.java (Model):**
   https://github.com/Aslam-0305/java_capstone/blob/main/app/src/main/java/com/project/back_end/models/Appointment.java

---

## How to Apply These Changes:

If you need to recreate the database with these changes:

```sql
-- Drop existing procedures
DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor;
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth;
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear;

-- Re-run the updated stored_procedures.sql file
SOURCE database/stored_procedures.sql;

-- Verify procedures were created
SHOW PROCEDURE STATUS WHERE Db = 'smartcare_clinic';
```

All changes have been committed and pushed to your GitHub repository!
