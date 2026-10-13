package com.smartcare.repository;

import com.smartcare.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization")
    List<Doctor> findBySpecialization(@Param("specialization") String specialization);

    @Query("SELECT d FROM Doctor d JOIN d.user u WHERE u.username = :username")
    Optional<Doctor> findByUsername(@Param("username") String username);

    @Query("SELECT d FROM Doctor d WHERE d.user.id = :userId")
    Optional<Doctor> findByUserId(@Param("userId") Integer userId);

    List<Doctor> findAll();
}
