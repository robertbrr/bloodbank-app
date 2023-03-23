package com.example.application.views.list.adminView;

import com.example.application.data.entity.Doctor;
import com.example.application.data.entity.DonationCenter;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;


public class DoctorForm extends FormLayout {
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField CNP = new TextField("CNP");
    private EmailField email = new EmailField("Email");
    private Binder <Doctor> binder = new BeanValidationBinder<>(Doctor.class);
    private ComboBox<DonationCenter> donationCenterComboBox = new ComboBox<>("Donation Center");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Doctor doctor;

    public DoctorForm(List<DonationCenter> donationCenters) {
        addClassName("contact-form");
        donationCenterComboBox.setItems(donationCenters);
        donationCenterComboBox.setItemLabelGenerator(DonationCenter::getName);
        binder.bindInstanceFields(this);
        add(
                username,
                password,
                firstName,
                lastName,
                CNP,
                email,
                donationCenterComboBox,
                createButtonsLayout());
    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
        binder.readBean(doctor);
        if(doctor!=null) {
            donationCenterComboBox.setValue(doctor.getDonationCenter());
        }
    }

    public Doctor getDoctor() {
        try {
            binder.writeBean(doctor);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        doctor.setRole("ROLE_DOCTOR");
        doctor.setDonationCenter(donationCenterComboBox.getValue());
        return doctor;
    }

    public boolean donationCenterNotSet(){
        return donationCenterComboBox.isEmpty();
    }

    public void addSaveListener(ComponentEventListener<ClickEvent<Button>> eventListener){
        save.addClickListener(eventListener);
    }

    public void addCloseListener(ComponentEventListener<ClickEvent<Button>> eventListener){
        close.addClickListener(eventListener);
    }

    public void addDeleteListener(ComponentEventListener<ClickEvent<Button>> eventListener){
        delete.addClickListener(eventListener);
    }

}

