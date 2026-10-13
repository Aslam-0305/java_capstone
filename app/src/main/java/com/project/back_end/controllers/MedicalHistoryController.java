package com.project.back_end.controllers;

import com.project.back_end.models.MedicalHistory;
import com.project.back_end.models.Patient;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.User;
import com.project.back_end.repo.PatientRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.UserRepository;
import com.project.back_end.services.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for Medical History endpoints
 * Handles medical record operations
 */
@RestController
@RequestMapping("/api/medical-history")
@CrossOrigin(origins = "*")
public class MedicalHistoryController {
    
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Create medical history record
     */
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> createMedicalHistory(@RequestBody MedicalHistory request) {
        try {
            Appointment appointment = appointmentRepository.findById(request.getAppointment().getId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
            String email = (String) org.springframework.security.core.context.SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Doctor doctor = doctorRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
            Patient patient = patientRepository.findById(appointment.getPatient().getId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            
            MedicalHistory history = medicalHistoryService.createMedicalHistory(
                    appointment, doctor, patient,
                    request.getDiagnosis(),
                    request.getTreatment(),
                    request.getMedications()
            );
            
            if (request.getNotes() != null) {
                history.setNotes(request.getNotes());
            }
            if (request.getFollowUpRequired() != null) {
                history.setFollowUpRequired(request.getFollowUpRequired());
            }
            if (request.getFollowUpDate() != null) {
                history.setFollowUpDate(request.getFollowUpDate());
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", history.getId());
            response.put("message", "Medical history recorded successfully");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Get medical history for a patient
     */
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<?> getPatientMedicalHistory(@PathVariable Long patientId) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            
            List<MedicalHistory> histories = medicalHistoryService.getPatientMedicalHistory(patient);
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", histories);
            response.put("total", histories.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Get medical history by appointment
     */
    @GetMapping("/appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<?> getAppointmentMedicalHistory(@PathVariable Long appointmentId) {
        try {
            Optional<MedicalHistory> history = medicalHistoryService.getAppointmentMedicalHistory(appointmentId);
            
            if (history.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical history not found");
            }
            
            return ResponseEntity.ok(history.get());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Get medical history for patient in date range
     */
    @GetMapping("/patient/{patientId}/range")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<?> getPatientMedicalHistoryByDateRange(
            @PathVariable Long patientId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            List<MedicalHistory> histories = medicalHistoryService.getPatientMedicalHistoryByDateRange(
                    patient, start, end
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", histories);
            response.put("total", histories.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Update medical history
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> updateMedicalHistory(@PathVariable Long id, @RequestBody MedicalHistory updateRequest) {
        try {
            MedicalHistory updated = medicalHistoryService.updateMedicalHistory(id, updateRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", updated.getId());
            response.put("message", "Medical history updated successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
