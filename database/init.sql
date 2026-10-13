CREATE DATABASE IF NOT EXISTS smartcare_clinic;
USE smartcare_clinic;

-- Users Table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    role ENUM('ADMIN', 'DOCTOR', 'PATIENT') NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_role (role),
    INDEX idx_status (status)
);

-- Doctors Table
CREATE TABLE doctors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    specialization VARCHAR(100) NOT NULL,
    license_number VARCHAR(50) UNIQUE NOT NULL,
    experience_years INT,
    bio TEXT,
    consultation_fee DECIMAL(10,2),
    available_hours VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_specialization (specialization)
);

-- Patients Table
CREATE TABLE patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    date_of_birth DATE,
    gender ENUM('M', 'F', 'OTHER'),
    blood_group VARCHAR(5),
    allergies TEXT,
    emergency_contact VARCHAR(100),
    emergency_phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Appointments Table
CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED', 'NO_SHOW') DEFAULT 'SCHEDULED',
    reason_for_visit VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
    UNIQUE KEY uk_doctor_datetime (doctor_id, appointment_date, start_time),
    INDEX idx_patient_id (patient_id),
    INDEX idx_doctor_id (doctor_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status)
);

-- Medical History Table
CREATE TABLE medical_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_id INT,
    diagnosis VARCHAR(255),
    treatment VARCHAR(255),
    notes TEXT,
    visit_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE SET NULL,
    INDEX idx_patient_id (patient_id),
    INDEX idx_visit_date (visit_date)
);

-- Insert Sample Data
INSERT INTO users (username, email, password_hash, full_name, phone, role, status) VALUES
('admin_clinic', 'admin@smartcare.com', '$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2', 'Admin User', '1234567890', 'ADMIN', 'ACTIVE');

INSERT INTO users (username, email, password_hash, full_name, phone, role, status) VALUES
('dr_emily', 'emily@smartcare.com', '$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2', 'Dr. Emily Adams', '9876543210', 'DOCTOR', 'ACTIVE'),
('dr_mark', 'mark@smartcare.com', '$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2', 'Dr. Mark Johnson', '9876543211', 'DOCTOR', 'ACTIVE'),
('dr_sarah', 'sarah@smartcare.com', '$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2', 'Dr. Sarah Lee', '9876543212', 'DOCTOR', 'ACTIVE');

INSERT INTO doctors (user_id, specialization, license_number, experience_years, consultation_fee, available_hours) VALUES
(2, 'Cardiology', 'LIC001', 10, 100.00, '09:00-12:00, 14:00-17:00'),
(3, 'Neurology', 'LIC002', 8, 90.00, '10:00-13:00, 15:00-18:00'),
(4, 'Orthopedics', 'LIC003', 12, 110.00, '08:00-11:00, 13:00-16:00');

INSERT INTO users (username, email, password_hash, full_name, phone, role, status) VALUES
('john_smith', 'john@email.com', '$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2', 'John Smith', '5555555555', 'PATIENT', 'ACTIVE'),
('jane_doe', 'jane@email.com', '$2a$10$dXJ3SW6G7P50eS3tLvtgmejNjyHxdgkl3Z5mHa4dxkHHZhNZSUSE2', 'Jane Doe', '5555555556', 'PATIENT', 'ACTIVE');

INSERT INTO patients (user_id, date_of_birth, gender, blood_group, allergies) VALUES
(5, '1990-05-15', 'M', 'O+', 'Penicillin'),
(6, '1985-03-22', 'F', 'B+', 'None');

INSERT INTO appointments (patient_id, doctor_id, appointment_date, start_time, end_time, status, reason_for_visit) VALUES
(1, 1, '2025-02-15', '10:00:00', '10:30:00', 'SCHEDULED', 'Cardiac Checkup'),
(2, 2, '2025-02-16', '11:00:00', '11:30:00', 'SCHEDULED', 'Headache consultation');
