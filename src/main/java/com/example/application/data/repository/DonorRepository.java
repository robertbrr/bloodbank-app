package com.example.application.data.repository;

import com.example.application.data.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor,Long> {
    List<Donor> findByFirstNameStartsWithOrLastNameStartsWith(String firstName, String lastName);

    Optional<Donor> findByUsernameLike(String username);

}
