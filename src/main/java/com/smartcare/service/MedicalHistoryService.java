package com.smartcare.service;

import com.smartcare.entity.MedicalHistory;
import com.smartcare.entity.Patient;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.Appointment;
import com.smartcare.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Medical History operations
 * Handles business logic for medical record management
 */
@Service
@Transactional
public class MedicalHistoryService {
    
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;
    
    /**
     * Create new medical history record
     */
    public MedicalHistory createMedicalHistory(Appointment appointment, Doctor doctor,
                                               Patient patient, String diagnosis,
                                               String treatment, String medications) {
        MedicalHistory history = new MedicalHistory();
        history.setAppointment(appointment);
        history.setDoctor(doctor);
        history.setPatient(patient);
        history.setDiagnosis(diagnosis);
        history.setTreatment(treatment);
        history.setMedications(medications);
        
        return medicalHistoryRepository.save(history);
    }
    
    /**
     * Get medical history by ID
     */
    public Optional<MedicalHistory> getMedicalHistoryById(Long id) {
        return medicalHistoryRepository.findById(id);
    }
    
    /**
     * Get all medical history for a patient
     */
    public List<MedicalHistory> getPatientMedicalHistory(Patient patient) {
        return medicalHistoryRepository.findByPatientOrderByCreatedAtDesc(patient);
    }
    
    /**
     * Get all medical history records by a doctor
     */
    public List<MedicalHistory> getDoctorMedicalHistory(Doctor doctor) {
        return medicalHistoryRepository.findByDoctorOrderByCreatedAtDesc(doctor);
    }
    
    /**
     * Get medical history for specific appointment
     */
    public Optional<MedicalHistory> getAppointmentMedicalHistory(Long appointmentId) {
        return medicalHistoryRepository.findByAppointmentId(appointmentId);
    }
    
    /**
     * Get medical history for patient within date range
     */
    public List<MedicalHistory> getPatientMedicalHistoryByDateRange(Patient patient,
                                                                      LocalDate startDate,
                                                                      LocalDate endDate) {
        return medicalHistoryRepository.findByPatientAndDateRange(patient, startDate, endDate);
    }
    
    /**
     * Update medical history
     */
    public MedicalHistory updateMedicalHistory(Long id, MedicalHistory updateRequest) {
        MedicalHistory history = medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical history not found"));
        
        if (updateRequest.getDiagnosis() != null) {
            history.setDiagnosis(updateRequest.getDiagnosis());
        }
        if (updateRequest.getTreatment() != null) {
            history.setTreatment(updateRequest.getTreatment());
        }
        if (updateRequest.getMedications() != null) {
            history.setMedications(updateRequest.getMedications());
        }
        if (updateRequest.getNotes() != null) {
            history.setNotes(updateRequest.getNotes());
        }
        if (updateRequest.getFollowUpRequired() != null) {
            history.setFollowUpRequired(updateRequest.getFollowUpRequired());
        }
        if (updateRequest.getFollowUpDate() != null) {
            history.setFollowUpDate(updateRequest.getFollowUpDate());
        }
        
        return medicalHistoryRepository.save(history);
    }
    
    /**
     * Delete medical history
     */
    public void deleteMedicalHistory(Long id) {
        medicalHistoryRepository.deleteById(id);
    }
    
    /**
     * Get medical history count for patient
     */
    public long getPatientMedicalHistoryCount(Patient patient) {
        return medicalHistoryRepository.countByPatient(patient);
    }
    
    /**
     * Get medical history count by doctor
     */
    public long getDoctorMedicalHistoryCount(Doctor doctor) {
        return medicalHistoryRepository.countByDoctor(doctor);
    }
    
    /**
     * Get all follow-ups required
     */
    public List<MedicalHistory> getFollowUpsRequired() {
        return medicalHistoryRepository.findByFollowUpRequiredTrueOrderByFollowUpDateAsc();
    }
}
