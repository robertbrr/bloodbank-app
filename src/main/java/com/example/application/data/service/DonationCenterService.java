package com.example.application.data.service;

import com.example.application.data.entity.DonationCenter;
import com.example.application.data.repository.DonationCenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationCenterService {
    private final DonationCenterRepository donationCenterRepository;

    public DonationCenterService(DonationCenterRepository donationCenterRepository) {
        this.donationCenterRepository = donationCenterRepository;
    }
    public List<DonationCenter> findAll() {
        return this.donationCenterRepository.findAll();
    }
}
