package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.AppointmentStatus;
import com.project.back_end.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public List<Appointment> getDoctorAppointmentsByDate(Integer doctorId, LocalDate date) {
        return appointmentRepository.findDoctorAppointmentsByDate(doctorId, date);
    }

    public List<Appointment> getPatientAppointments(Integer patientId) {
        return appointmentRepository.findPatientAppointments(patientId);
    }

    public List<Appointment> getDoctorAppointments(Integer doctorId) {
        return appointmentRepository.findDoctorAppointments(doctorId);
    }

    public List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findAppointmentsByDateRange(startDate, endDate);
    }

    public Appointment updateAppointment(Integer id, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (appointmentDetails.getAppointmentDate() != null) {
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        }
        if (appointmentDetails.getStartTime() != null) {
            appointment.setStartTime(appointmentDetails.getStartTime());
        }
        if (appointmentDetails.getEndTime() != null) {
            appointment.setEndTime(appointmentDetails.getEndTime());
        }
        if (appointmentDetails.getStatus() != null) {
            appointment.setStatus(appointmentDetails.getStatus());
        }
        if (appointmentDetails.getReasonForVisit() != null) {
            appointment.setReasonForVisit(appointmentDetails.getReasonForVisit());
        }
        if (appointmentDetails.getNotes() != null) {
            appointment.setNotes(appointmentDetails.getNotes());
        }

        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Integer id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }
}
