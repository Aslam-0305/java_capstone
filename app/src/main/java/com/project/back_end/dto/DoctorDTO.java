package com.project.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String specialization;
    private String licenseNumber;
    private Integer experienceYears;
    private String bio;
    private BigDecimal consultationFee;
    private String availableHours;
}
