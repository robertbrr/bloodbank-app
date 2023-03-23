package com.example.application.views.list.donorView;

import com.example.application.data.entity.Appointment;
import com.example.application.data.entity.DonationCenter;
import com.example.application.data.entity.Donor;
import com.example.application.data.service.AppointmentService;
import com.example.application.views.list.DialogBoxHandler;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class AppointmentForm extends FormLayout {
    TextField label = new TextField("Location");
    Button save = new Button("Save");
    Button close = new Button("Cancel");
    DatePicker datePicker = new DatePicker("Date");

    DonationCenter donationCenter;
    Donor donor;
    Appointment appointment;
    AppointmentService appointmentService;

    public AppointmentForm(AppointmentService appointmentService) {
        addClassName("contact-form");
        this.appointmentService=appointmentService;
        label.setReadOnly(true);
        datePicker.setMin(LocalDate.from(LocalDateTime.now()));

        add(label,datePicker,createButtonsLayout());
        this.setMaxWidth("15em");
    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> this.setVisible(false));

        return new HorizontalLayout(save, close);
    }

    public void setDonationCenter(DonationCenter donationCenter){
        this.donationCenter = donationCenter;
        if(donationCenter!=null) {
            label.setValue(donationCenter.getName());
        }
    }
    public void setDonor(Donor donor){
        this.donor=donor;
    }
    public void setAppointment(Appointment appointment){
        this.appointment=appointment;
    }

    private void validateAndSave() {
        try {
            appointment = new Appointment();
            appointment.setDonor(donor);
            appointment.setDonationCenter(donationCenter);
            appointment.setStatus("PENDING");
            LocalDateTime appointmentDate = LocalDateTime.from(datePicker.getValue().atStartOfDay());
            appointment.setDate(appointmentDate);
            long appointmentsOnDate = appointmentService.getAppointmentsOnDay(donationCenter.getId(),appointmentDate);
            if(appointmentsOnDate>=donationCenter.getMaxDonationsPerDay()){
                DialogBoxHandler.showErrorNotification("Day Full");
            }
            else if (datePicker.getValue().getDayOfWeek().equals(DayOfWeek.SATURDAY)||datePicker.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                DialogBoxHandler.showErrorNotification("Can't schedule on weekends.");
            }
            else{
                appointmentService.saveAppointment(appointment);
                this.setVisible(false);
            }
        }catch(Exception e){
            e.printStackTrace();
            DialogBoxHandler.showErrorNotification("Invalid date");
        }
    }
}

