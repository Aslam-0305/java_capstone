package com.smartcare.service;

import com.smartcare.dto.LoginRequest;
import com.smartcare.entity.Patient;
import com.smartcare.entity.User;
import com.smartcare.repository.PatientRepository;
import com.smartcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Patient operations
 * Handles business logic for patient management
 */
@Service
@Transactional
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Register a new patient
     */
    public Patient registerPatient(String fullName, String email, String phone,
                                   String password, String dateOfBirth, String gender,
                                   String bloodGroup) {
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create user
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("PATIENT");
        user.setIsActive(true);
        userRepository.save(user);
        
        // Create patient
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setDateOfBirth(dateOfBirth);
        patient.setGender(gender);
        patient.setBloodGroup(bloodGroup);
        
        return patientRepository.save(patient);
    }
    
    /**
     * Get patient by ID
     */
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    
    /**
     * Get patient by user ID
     */
    public Optional<Patient> getPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId);
    }
    
    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    /**
     * Update patient profile
     */
    public Patient updatePatientProfile(Long patientId, Patient updateRequest) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        if (updateRequest.getDateOfBirth() != null) {
            patient.setDateOfBirth(updateRequest.getDateOfBirth());
        }
        if (updateRequest.getGender() != null) {
            patient.setGender(updateRequest.getGender());
        }
        if (updateRequest.getBloodGroup() != null) {
            patient.setBloodGroup(updateRequest.getBloodGroup());
        }
        if (updateRequest.getAllergies() != null) {
            patient.setAllergies(updateRequest.getAllergies());
        }
        if (updateRequest.getEmergencyContact() != null) {
            patient.setEmergencyContact(updateRequest.getEmergencyContact());
        }
        if (updateRequest.getEmergencyPhone() != null) {
            patient.setEmergencyPhone(updateRequest.getEmergencyPhone());
        }
        if (updateRequest.getAddress() != null) {
            patient.setAddress(updateRequest.getAddress());
        }
        if (updateRequest.getCity() != null) {
            patient.setCity(updateRequest.getCity());
        }
        if (updateRequest.getState() != null) {
            patient.setState(updateRequest.getState());
        }
        
        return patientRepository.save(patient);
    }
    
    /**
     * Delete patient
     */
    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }
    
    /**
     * Get patient count
     */
    public long getPatientCount() {
        return patientRepository.count();
    }
    
    /**
     * Get patients by blood group
     */
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }
}
