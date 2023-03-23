package com.example.application.data.service;

import com.example.application.data.entity.Donor;
import com.example.application.data.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {
    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public List<Donor> findAllDonorsByName(String filter){
        return this.donorRepository.findByFirstNameStartsWithOrLastNameStartsWith(filter,filter);
    }

    public void save(Donor donor){
        this.donorRepository.save(donor);
    }

    public Optional<Donor> findDonorByUsername(String username){
        return this.donorRepository.findByUsernameLike(username);
    }
    public void deleteDonorById(Long id){
        this.donorRepository.deleteById(id);
    }
}
