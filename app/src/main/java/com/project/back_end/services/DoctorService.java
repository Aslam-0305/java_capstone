package com.project.back_end.services;

import com.project.back_end.dto.DoctorDTO;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public DoctorDTO addDoctor(Doctor doctor) {
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    public DoctorDTO getDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return convertToDTO(doctor);
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Patient> getPatientsByDoctor(Integer doctorId) {
        return patientRepository.findPatientsByDoctor(doctorId);
    }

    public DoctorDTO updateDoctor(Integer id, Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        if (doctorDetails.getSpecialization() != null) {
            doctor.setSpecialization(doctorDetails.getSpecialization());
        }
        if (doctorDetails.getExperienceYears() != null) {
            doctor.setExperienceYears(doctorDetails.getExperienceYears());
        }
        if (doctorDetails.getBio() != null) {
            doctor.setBio(doctorDetails.getBio());
        }
        if (doctorDetails.getConsultationFee() != null) {
            doctor.setConsultationFee(doctorDetails.getConsultationFee());
        }
        if (doctorDetails.getAvailableHours() != null) {
            doctor.setAvailableHours(doctorDetails.getAvailableHours());
        }

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToDTO(updatedDoctor);
    }

    public void deleteDoctor(Integer id) {
        doctorRepository.deleteById(id);
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .fullName(doctor.getUser().getFullName())
                .email(doctor.getUser().getEmail())
                .phone(doctor.getUser().getPhone())
                .specialization(doctor.getSpecialization())
                .licenseNumber(doctor.getLicenseNumber())
                .experienceYears(doctor.getExperienceYears())
                .bio(doctor.getBio())
                .consultationFee(doctor.getConsultationFee())
                .availableHours(doctor.getAvailableHours())
                .build();
    }
}

    // Get available time slots for a doctor on a specific date
    public List<String> getAvailableTimeSlots(Integer doctorId, String date, String token) {
        // Validate token first
        if (!validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Parse available hours and return time slots
        // This is a simplified implementation
        List<String> timeSlots = java.util.Arrays.asList(
                "09:00-09:30", "09:30-10:00", "10:00-10:30", "10:30-11:00",
                "11:00-11:30", "11:30-12:00", "14:00-14:30", "14:30-15:00",
                "15:00-15:30", "15:30-16:00", "16:00-16:30", "16:30-17:00"
        );
        return timeSlots;
    }

    // Validate doctor login credentials
    public boolean validateDoctorLogin(String email, String password) {
        return doctorRepository.findByEmail(email)
                .map(doctor -> {
                    // In production, use proper password hashing (bcrypt)
                    // This is a simplified version
                    return doctor.getUser().getPasswordHash().equals(password);
                })
                .orElse(false);
    }

    // Token validation helper method
    private boolean validateToken(String token) {
        // In production, implement proper JWT token validation
        // This is a simplified version
        return token != null && !token.isEmpty() && token.length() > 10;
    }
