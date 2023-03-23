package com.example.application.data.service;

import com.example.application.data.entity.Appointment;
import com.example.application.data.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    public void saveAppointment(Appointment appointment){
        this.appointmentRepository.save(appointment);
    }
    public long getAppointmentsOnDay(Long id, LocalDateTime date){
        return this.appointmentRepository.countByDonationCenter_IdAndDate(id,date);
    }
    public List<Appointment> findAll(){
        return this.appointmentRepository.findAll();
    }

    public void deleteById(Long id){
        this.appointmentRepository.deleteById(id);
    }
    public List<Appointment> findByDonationCenter_Id(Long id){
        return this.appointmentRepository.findByDonationCenter_Id(id);
    }
    public List<Appointment> findByDonorId(Long id){
        return this.appointmentRepository.findByDonor_Id(id);
    }

    public void deleteByDonorId(Long id){
        this.appointmentRepository.deleteByDonor_Id(id);
    }
}
