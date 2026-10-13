package com.project.back_end.repo;

import com.project.back_end.models.Prescription;
import com.project.back_end.models.Patient;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Prescription entity
 * Handles database operations for prescription records
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    /**
     * Find all prescriptions for a specific appointment
     */
    List<Prescription> findByAppointmentOrderByCreatedAtDesc(Appointment appointment);
    
    /**
     * Find all prescriptions for a patient
     */
    List<Prescription> findByPatientOrderByCreatedAtDesc(Patient patient);
    
    /**
     * Find all active prescriptions for a patient
     */
    List<Prescription> findByPatientAndIsActiveTrueOrderByCreatedAtDesc(Patient patient);
    
    /**
     * Find all prescriptions issued by a doctor
     */
    List<Prescription> findByDoctorOrderByCreatedAtDesc(Doctor doctor);
    
    /**
     * Find prescriptions for a patient from a specific appointment
     */
    @Query("SELECT p FROM Prescription p WHERE p.patient = :patient " +
           "AND p.appointment = :appointment ORDER BY p.createdAt DESC")
    List<Prescription> findByPatientAndAppointment(@Param("patient") Patient patient,
                                                    @Param("appointment") Appointment appointment);
    
    /**
     * Find active prescriptions for a patient within date range
     */
    @Query("SELECT p FROM Prescription p WHERE p.patient = :patient " +
           "AND p.isActive = true " +
           "AND p.createdAt >= :startDate AND p.createdAt <= :endDate " +
           "ORDER BY p.createdAt DESC")
    List<Prescription> findActivePrescriptionsByDateRange(@Param("patient") Patient patient,
                                                          @Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate);
    
    /**
     * Count total prescriptions for a patient
     */
    long countByPatient(Patient patient);
    
    /**
     * Count active prescriptions for a patient
     */
    long countByPatientAndIsActiveTrue(Patient patient);
    
    /**
     * Find all active prescriptions
     */
    List<Prescription> findByIsActiveTrueOrderByCreatedAtDesc();
    
    /**
     * Check if prescription exists for appointment
     */
    boolean existsByAppointment(Appointment appointment);
}
