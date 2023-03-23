package com.example.application.views.list.mainView;

import com.example.application.data.entity.Donor;
import com.example.application.data.entity.User;
import com.example.application.data.service.DonorService;
import com.example.application.data.service.UserService;
import com.example.application.views.list.DialogBoxHandler;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Optional;

@Route("register")
@PageTitle("Register")
@AnonymousAllowed

public class RegisterView extends VerticalLayout{

    private RegisterForm registerForm;

    private DonorService donorService;
    private UserService userService;

    public RegisterView(DonorService donorService, UserService userService){
        this.donorService=donorService;
        this.userService=userService;
        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        configureForm();
        add(new H1("Enter your information:"), new HorizontalLayout(registerForm));

    }
    private void configureForm() {
        registerForm = new RegisterForm();
        registerForm.setWidth("25em");
        registerForm.setDonor(new Donor());
        registerForm.addSaveListener(e->validateAndSave());
    }

    private void validateAndSave() {
        Donor donor = registerForm.getDonor();
        Optional<User> user = userService.findUserByUsername(donor.getUsername());
        if(user.isEmpty()) {
            if (registerForm.passwordsMatch()) {
                Dialog dialog = DialogBoxHandler.makeDialogBox("Account successfully created!");
                dialog.addOpenedChangeListener(e -> {
                    if (!e.isOpened()) {
                        UI.getCurrent().navigate("/login");
                    }
                });
                donorService.save(donor);
            } else {
                DialogBoxHandler.showErrorNotification("Passwords do not match!");
            }
        }
        else{
            DialogBoxHandler.showErrorNotification("This username is already taken");
        }
    }
}