USE smartcare_clinic;

-- Stored Procedure 1: Get Daily Appointment Report by Doctor
DELIMITER $$
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN p_doctor_id INT,
    IN p_appointment_date DATE
)
BEGIN
    SELECT 
        a.id AS appointment_id,
        CONCAT(p.full_name) AS patient_name,
        d_user.full_name AS doctor_name,
        p.phone AS patient_phone,
        a.appointment_date,
        CONCAT(a.appointment_date, ' ', a.start_time) AS appointment_time,
        a.start_time,
        a.end_time,
        a.reason_for_visit,
        a.status
    FROM appointments a
    INNER JOIN patients pat ON a.patient_id = pat.id
    INNER JOIN users p ON pat.user_id = p.id
    INNER JOIN doctors doc ON a.doctor_id = doc.id
    INNER JOIN users d_user ON doc.user_id = d_user.id
    WHERE a.doctor_id = p_doctor_id 
    AND a.appointment_date = p_appointment_date
    ORDER BY a.start_time ASC;
END$$
DELIMITER ;

-- Stored Procedure 2: Get Doctor with Most Patients by Month
DELIMITER $$
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN p_month INT,
    IN p_year INT
)
BEGIN
    SELECT 
        d.id AS doctor_id,
        u.full_name AS doctor_name,
        d.specialization,
        COUNT(DISTINCT a.patient_id) AS patients_seen,
        COUNT(a.id) AS total_appointments,
        MONTH(a.appointment_date) AS month,
        YEAR(a.appointment_date) AS year
    FROM doctors d
    INNER JOIN users u ON d.user_id = u.id
    LEFT JOIN appointments a ON d.id = a.doctor_id
    WHERE MONTH(a.appointment_date) = p_month
    AND YEAR(a.appointment_date) = p_year
    AND a.status IN ('SCHEDULED', 'COMPLETED')
    GROUP BY d.id
    ORDER BY patients_seen DESC
    LIMIT 1;
END$$
DELIMITER ;

-- Stored Procedure 3: Get Doctor with Most Patients by Year
DELIMITER $$
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN p_year INT
)
BEGIN
    SELECT 
        d.id AS doctor_id,
        u.full_name AS doctor_name,
        d.specialization,
        COUNT(DISTINCT a.patient_id) AS patients_seen,
        COUNT(a.id) AS total_appointments,
        YEAR(a.appointment_date) AS year
    FROM doctors d
    INNER JOIN users u ON d.user_id = u.id
    LEFT JOIN appointments a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_date) = p_year
    AND a.status IN ('SCHEDULED', 'COMPLETED')
    GROUP BY d.id
    ORDER BY patients_seen DESC
    LIMIT 1;
END$$
DELIMITER ;

-- Stored Procedure 4: Get Appointment Statistics
DELIMITER $$
CREATE PROCEDURE GetAppointmentStatistics(
    IN p_start_date DATE,
    IN p_end_date DATE
)
BEGIN
    SELECT 
        COUNT(*) AS total_appointments,
        SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed,
        SUM(CASE WHEN status = 'SCHEDULED' THEN 1 ELSE 0 END) AS scheduled,
        SUM(CASE WHEN status = 'CANCELLED' THEN 1 ELSE 0 END) AS cancelled,
        SUM(CASE WHEN status = 'NO_SHOW' THEN 1 ELSE 0 END) AS no_show,
        COUNT(DISTINCT doctor_id) AS total_doctors,
        COUNT(DISTINCT patient_id) AS total_patients
    FROM appointments
    WHERE appointment_date BETWEEN p_start_date AND p_end_date;
END$$
DELIMITER ;
