package com.project.back_end.repo;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.username = :username")
    Optional<Patient> findByUsername(@Param("username") String username);

    @Query("SELECT p FROM Patient p WHERE p.user.id = :userId")
    Optional<Patient> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT p FROM Patient p JOIN p.appointments a WHERE a.doctor.id = :doctorId GROUP BY p.id")
    List<Patient> findPatientsByDoctor(@Param("doctorId") Integer doctorId);

    // Find patient by email address
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.email = :email")
    Optional<Patient> findByEmail(@Param("email") String email);

    // Find patient by phone number
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.phone = :phone")
    Optional<Patient> findByPhone(@Param("phone") String phone);

    // Find patient by email or phone
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.email = :email OR u.phone = :phone")
    Optional<Patient> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);

    List<Patient> findAll();
}
