package com.example.application.data.repository;

import com.example.application.data.entity.DonationCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationCenterRepository extends JpaRepository<DonationCenter,Long> {
}
