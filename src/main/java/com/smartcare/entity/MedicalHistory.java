package com.smartcare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(length = 255)
    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @Column(length = 255)
    private String treatment;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    @NotNull(message = "Visit date is required")
    @PastOrPresent(message = "Visit date cannot be in future")
    private LocalDate visitDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
