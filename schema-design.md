# Database Schema Design - SmartCare Clinic Management System

## Overview
The SmartCare Clinic Management System uses a hybrid database architecture:
- **MySQL 8.0** for relational data (users, doctors, patients, appointments)
- **MongoDB 5.0** for flexible prescription storage

## Entity Relationship Diagram (ERD)

```
┌─────────────────────────────────────────────────────────────────┐
│                          USERS (Base)                            │
├─────────────────────────────────────────────────────────────────┤
│ id (PK)                                                           │
│ full_name                                                         │
│ email (UNIQUE)                                                    │
│ phone                                                             │
│ password_hash                                                     │
│ role (ADMIN, DOCTOR, PATIENT)                                   │
│ is_active                                                         │
│ created_at                                                        │
│ updated_at                                                        │
└─────────────────────────────────────────────────────────────────┘
         ▲                          ▲                      ▲
         │ JOINED INHERITANCE       │                      │
    ┌────┴─────────┐               │                      │
    │              │               │                      │
    │              │               │                      │
┌───────────┐  ┌──────────┐   ┌──────────┐        ┌──────────────┐
│ DOCTORS   │  │ PATIENTS │   │  ADMINS  │        │ APPOINTMENTS │
├───────────┤  ├──────────┤   ├──────────┤        ├──────────────┤
│ user_id   │  │ user_id  │   │ user_id  │        │ id (PK)      │
│ license   │  │ dob      │   │ dept     │        │ doctor_id(FK)│
│ spec      │  │ blood    │   │ salary   │        │ patient_id(FK)
│ exp_years │  │ allergies│   │          │        │ date         │
│           │  │ address  │   │          │        │ start_time   │
└───────────┘  └──────────┘   └──────────┘        │ end_time     │
                                                    │ reason       │
    ◄────────────────────────────────────────────  │ status       │
    1 Doctor : Many Appointments                    │ created_at   │
                                                    └──────────────┘
                                                           │
    ◄──────────────────────────────────────────────────   │
    1 Patient : Many Appointments                         │
                                                          │
                                        ┌─────────────────┘
                                        │
                                   ┌────▼──────────────┐
                                   │ MEDICAL_HISTORY  │
                                   ├──────────────────┤
                                   │ id (PK)          │
                                   │ appt_id (FK)     │
                                   │ doctor_id (FK)   │
                                   │ patient_id (FK)  │
                                   │ diagnosis        │
                                   │ treatment        │
                                   │ created_at       │
                                   └──────────────────┘
```

## MySQL Tables

### 1. USERS Table (Base Entity)
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'DOCTOR', 'PATIENT') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role)
);
```

### 2. DOCTORS Table (Specialization of USERS)
```sql
CREATE TABLE doctors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    license_number VARCHAR(100) UNIQUE NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    experience_years INT,
    qualification VARCHAR(255),
    consultation_fee DECIMAL(10, 2),
    availability_status ENUM('AVAILABLE', 'ON_LEAVE', 'RETIRED') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_specialization (specialization),
    INDEX idx_license (license_number)
);
```

### 3. PATIENTS Table (Specialization of USERS)
```sql
CREATE TABLE patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    date_of_birth DATE,
    gender ENUM('M', 'F', 'OTHER'),
    blood_group VARCHAR(5),
    allergies TEXT,
    emergency_contact VARCHAR(255),
    emergency_phone VARCHAR(20),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_blood_group (blood_group)
);
```

### 4. APPOINTMENTS Table
```sql
CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    reason_for_visit VARCHAR(500),
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED', 'NO_SHOW') DEFAULT 'SCHEDULED',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    INDEX idx_doctor_id (doctor_id),
    INDEX idx_patient_id (patient_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status),
    UNIQUE KEY unique_appointment (doctor_id, patient_id, appointment_date, start_time)
);
```

### 5. MEDICAL_HISTORY Table
```sql
CREATE TABLE medical_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    diagnosis TEXT NOT NULL,
    treatment TEXT,
    medications TEXT,
    notes TEXT,
    follow_up_required BOOLEAN DEFAULT FALSE,
    follow_up_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    INDEX idx_appointment_id (appointment_id),
    INDEX idx_patient_id (patient_id),
    INDEX idx_created_at (created_at)
);
```

### 6. PRESCRIPTIONS Table
```sql
CREATE TABLE prescriptions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    medicine_name VARCHAR(255) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    frequency VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    duration_unit ENUM('DAYS', 'WEEKS', 'MONTHS') DEFAULT 'DAYS',
    instructions TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    INDEX idx_patient_id (patient_id),
    INDEX idx_appointment_id (appointment_id)
);
```

## MongoDB Collections

### PRESCRIPTIONS Collection (NoSQL)
```json
{
  "_id": ObjectId,
  "appointmentId": 1,
  "doctorId": 1,
  "patientId": 1,
  "medicines": [
    {
      "name": "Aspirin",
      "dosage": "500mg",
      "frequency": "Once daily",
      "duration": {
        "value": 7,
        "unit": "days"
      },
      "instructions": "Take with food",
      "sideEffects": ["Nausea", "Headache"]
    }
  ],
  "additionalNotes": "Follow-up in 1 week",
  "createdAt": ISODate(),
  "updatedAt": ISODate()
}
```

## Key Design Decisions

### 1. Inheritance Strategy: JOINED
- **Why**: Clean separation of concerns, efficient queries on specific types
- **Alternative Rejected**: Single table (wasted space), Table-per-class (join complexity)

### 2. Appointment Uniqueness
- Composite unique constraint prevents double-booking
- Allows reschedule by cancelling + rebooking

### 3. Foreign Key Constraints
- CASCADE on patient deletion (appointment history preserved in medical history)
- RESTRICT on doctor deletion (maintains referential integrity)

### 4. Soft Delete (Not Used)
- Hard delete for simplicity in capstone
- Can be added with `deleted_at` timestamp if needed

### 5. Audit Timestamps
- `created_at` and `updated_at` on all tables
- Enables audit trails and sorting

### 6. Polyglot Persistence
- MySQL for structured, relational data
- MongoDB for flexible prescription schemas (future expansion)

## Indexing Strategy

| Table | Column | Reason |
|-------|--------|--------|
| users | email | Login, unique constraint |
| doctors | specialization | Search by specialty |
| doctors | license_number | Unique constraint |
| appointments | doctor_id | Frequent queries by doctor |
| appointments | patient_id | Frequent queries by patient |
| appointments | appointment_date | Range queries |
| appointments | status | Filtering |
| medical_history | patient_id | View patient history |

## Sample Data Volume Expectations

| Table | Expected Rows | Notes |
|-------|---------------|-------|
| users | 1,000 | Admin + Doctors + Patients |
| doctors | 50 | Avg 20 per specialization |
| patients | 800 | Regular + new |
| appointments | 10,000 | 20 per patient/year |
| medical_history | 8,000 | 80% completion |
| prescriptions | 12,000 | Multi-medicine per history |

## Performance Considerations

1. **Query Optimization**: All tables have proper indexes
2. **Denormalization**: Medical history maintains doctor_id and patient_id for faster queries
3. **Pagination**: API endpoints support limit/offset
4. **Caching**: Frequent doctor lists cached in Redis (optional)
5. **Archival**: Old appointments (>2 years) can be moved to archive tables

## Backup Strategy

1. Daily incremental backups
2. Weekly full backups
3. MongoDB oplog for point-in-time recovery
4. Off-site backup storage
