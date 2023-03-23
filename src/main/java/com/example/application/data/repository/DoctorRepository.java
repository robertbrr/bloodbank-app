package com.example.application.data.repository;

import com.example.application.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor> findByFirstNameStartsWithOrLastNameStartsWith(String firstName, String lastName);

    Optional<Doctor> findByUsername(String username);

}
