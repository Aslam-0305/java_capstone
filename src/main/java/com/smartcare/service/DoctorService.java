package com.smartcare.service;

import com.smartcare.dto.DoctorDTO;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.Patient;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.repository.PatientRepository;
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
