package com.smartcare.repository;

import com.smartcare.entity.MedicalHistory;
import com.smartcare.entity.Patient;
import com.smartcare.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for MedicalHistory entity
 * Handles database operations for medical records
 */
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    
    /**
     * Find all medical history records for a patient
     */
    List<MedicalHistory> findByPatientOrderByCreatedAtDesc(Patient patient);
    
    /**
     * Find all medical history records by doctor
     */
    List<MedicalHistory> findByDoctorOrderByCreatedAtDesc(Doctor doctor);
    
    /**
     * Find medical history for a specific appointment
     */
    Optional<MedicalHistory> findByAppointmentId(Long appointmentId);
    
    /**
     * Find medical history for a patient within a date range
     */
    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.patient = :patient " +
           "AND mh.createdAt >= :startDate AND mh.createdAt <= :endDate " +
           "ORDER BY mh.createdAt DESC")
    List<MedicalHistory> findByPatientAndDateRange(@Param("patient") Patient patient,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
    
    /**
     * Count medical history records for a patient
     */
    long countByPatient(Patient patient);
    
    /**
     * Count medical history records by doctor
     */
    long countByDoctor(Doctor doctor);
    
    /**
     * Find all records with follow-up required
     */
    List<MedicalHistory> findByFollowUpRequiredTrueOrderByFollowUpDateAsc();
}
