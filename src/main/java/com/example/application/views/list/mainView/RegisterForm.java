package com.example.application.views.list.mainView;

import com.example.application.data.entity.Donor;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class RegisterForm extends FormLayout {
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Enter password");
    private PasswordField passwordConfirm = new PasswordField("Confirm password");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email");
    private Binder<Donor> binder = new BeanValidationBinder<>(Donor.class);

    private Button save = new Button("Save");
    private Button close = new Button("Back");
    private Donor donor;



    public RegisterForm() {
        addClassName("register-form");
        binder.bindInstanceFields(this);
        binder.forField(firstName).bind(Donor::getFirstName, Donor::setFirstName);
        binder.forField(lastName).bind(Donor::getLastName, Donor::setLastName);
        binder.forField(username).bind(Donor::getUsername, Donor::setUsername);
        binder.forField(password).bind(Donor::getPassword, Donor::setPassword);
        binder.forField(email).bind(Donor::getEmail, Donor::setEmail);
        add(    firstName,
                lastName,
                username,
                password,
                passwordConfirm,
                email,
                createButtonsLayout());
    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        close.addClickListener(e -> close.getUI().ifPresent(ui -> ui.navigate("/login")));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, close);
    }

    public void setDonor(Donor donor){
        this.donor = donor;
        binder.readBean(donor);
    }

    public void addSaveListener(ComponentEventListener<ClickEvent<Button>> eventListener){
        save.addClickListener(eventListener);
    }

    public Donor getDonor(){
        try {
            binder.writeBean(donor);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        donor.setRole("ROLE_DONOR");
        return donor;
    }

    public boolean passwordsMatch(){
        return this.password.getValue().equals(this.passwordConfirm.getValue());
    }

}

