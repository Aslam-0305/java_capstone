package com.smartcare.service;

import com.smartcare.entity.Prescription;
import com.smartcare.entity.Patient;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.Appointment;
import com.smartcare.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Prescription operations
 * Handles business logic for prescription management
 */
@Service
@Transactional
public class PrescriptionService {
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    /**
     * Create new prescription
     */
    public Prescription createPrescription(Appointment appointment, Doctor doctor,
                                          Patient patient, String medicineName,
                                          String dosage, String frequency,
                                          Integer duration, Prescription.DurationUnit durationUnit) {
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setMedicineName(medicineName);
        prescription.setDosage(dosage);
        prescription.setFrequency(frequency);
        prescription.setDuration(duration);
        prescription.setDurationUnit(durationUnit);
        prescription.setIsActive(true);
        
        return prescriptionRepository.save(prescription);
    }
    
    /**
     * Get prescription by ID
     */
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }
    
    /**
     * Get all prescriptions for an appointment
     */
    public List<Prescription> getAppointmentPrescriptions(Appointment appointment) {
        return prescriptionRepository.findByAppointmentOrderByCreatedAtDesc(appointment);
    }
    
    /**
     * Get all prescriptions for a patient
     */
    public List<Prescription> getPatientPrescriptions(Patient patient) {
        return prescriptionRepository.findByPatientOrderByCreatedAtDesc(patient);
    }
    
    /**
     * Get active prescriptions for a patient
     */
    public List<Prescription> getPatientActivePrescriptions(Patient patient) {
        return prescriptionRepository.findByPatientAndIsActiveTrueOrderByCreatedAtDesc(patient);
    }
    
    /**
     * Get all prescriptions by doctor
     */
    public List<Prescription> getDoctorPrescriptions(Doctor doctor) {
        return prescriptionRepository.findByDoctorOrderByCreatedAtDesc(doctor);
    }
    
    /**
     * Get prescriptions for patient from specific appointment
     */
    public List<Prescription> getAppointmentPatientPrescriptions(Patient patient, Appointment appointment) {
        return prescriptionRepository.findByPatientAndAppointment(patient, appointment);
    }
    
    /**
     * Get active prescriptions for patient within date range
     */
    public List<Prescription> getActivePrescriptionsByDateRange(Patient patient,
                                                                 LocalDateTime startDate,
                                                                 LocalDateTime endDate) {
        return prescriptionRepository.findActivePrescriptionsByDateRange(patient, startDate, endDate);
    }
    
    /**
     * Update prescription
     */
    public Prescription updatePrescription(Long id, Prescription updateRequest) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        
        if (updateRequest.getMedicineName() != null) {
            prescription.setMedicineName(updateRequest.getMedicineName());
        }
        if (updateRequest.getDosage() != null) {
            prescription.setDosage(updateRequest.getDosage());
        }
        if (updateRequest.getFrequency() != null) {
            prescription.setFrequency(updateRequest.getFrequency());
        }
        if (updateRequest.getDuration() != null) {
            prescription.setDuration(updateRequest.getDuration());
        }
        if (updateRequest.getDurationUnit() != null) {
            prescription.setDurationUnit(updateRequest.getDurationUnit());
        }
        if (updateRequest.getInstructions() != null) {
            prescription.setInstructions(updateRequest.getInstructions());
        }
        if (updateRequest.getIsActive() != null) {
            prescription.setIsActive(updateRequest.getIsActive());
        }
        
        return prescriptionRepository.save(prescription);
    }
    
    /**
     * Deactivate prescription
     */
    public Prescription deactivatePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        prescription.setIsActive(false);
        return prescriptionRepository.save(prescription);
    }
    
    /**
     * Delete prescription
     */
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
    
    /**
     * Get prescription count for patient
     */
    public long getPatientPrescriptionCount(Patient patient) {
        return prescriptionRepository.countByPatient(patient);
    }
    
    /**
     * Get active prescription count for patient
     */
    public long getPatientActivePrescriptionCount(Patient patient) {
        return prescriptionRepository.countByPatientAndIsActiveTrue(patient);
    }
    
    /**
     * Get all active prescriptions
     */
    public List<Prescription> getAllActivePrescriptions() {
        return prescriptionRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }
    
    /**
     * Check if prescription exists for appointment
     */
    public boolean prescriptionExistsForAppointment(Appointment appointment) {
        return prescriptionRepository.existsByAppointment(appointment);
    }
}
