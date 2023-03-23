package com.example.application.data.repository;

import com.example.application.data.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    @Query("select count(a) from Appointment a where a.donationCenter.id = ?1 and a.date = ?2")
    long countByDonationCenter_IdAndDate(Long id, LocalDateTime date);

    List<Appointment> findByDonationCenter_Id(Long id);

    List<Appointment> findByDonor_Id(Long id);

    long deleteByDonor_Id(Long id);

}
