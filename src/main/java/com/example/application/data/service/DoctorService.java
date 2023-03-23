package com.example.application.data.service;

import com.example.application.data.entity.Doctor;
import com.example.application.data.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAllDoctorsByName(String filter) {
        return this.doctorRepository.findByFirstNameStartsWithOrLastNameStartsWith(filter,filter);
    }

    public void saveDoctor(Doctor doctor){
        this.doctorRepository.save(doctor);
    }

    public Optional<Doctor> findByUsername(String username){
        return this.doctorRepository.findByUsername(username);
    }

    public void deleteDoctorById(Long id) {
        this.doctorRepository.deleteById(id);
    }
}
