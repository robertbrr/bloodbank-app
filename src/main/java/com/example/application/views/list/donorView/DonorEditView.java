package com.example.application.views.list.donorView;

import com.example.application.data.entity.Donor;
import com.example.application.data.service.AppointmentService;
import com.example.application.data.service.DonorService;
import com.example.application.security.SecurityService;
import com.example.application.views.list.DialogBoxHandler;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route("donor/edit")
@RolesAllowed("ROLE_DONOR")

public class DonorEditView extends VerticalLayout{

    private DonorEditForm donorEditForm;

    private DonorService donorService;
    private AppointmentService appointmentService;
    private SecurityService securityService;
    private Donor donor;

    public DonorEditView(DonorService donorService, AppointmentService appointmentService,SecurityService securityService){

        this.donorService=donorService;
        this.appointmentService=appointmentService;
        this.securityService=securityService;
        String username = securityService.getAuthenticatedUser().getUsername();
        this.donor = donorService.findDonorByUsername(username).get();

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        configureForm();
        add(new H1("Edit your information:"), new HorizontalLayout(donorEditForm));

    }
    private void configureForm() {
        donorEditForm = new DonorEditForm();
        donorEditForm.setWidth("25em");
        donorEditForm.setDonor(this.donor);
        donorEditForm.addDeleteListener(e->delete());
        donorEditForm.addSaveListener(e->validateAndSave());
    }


    private void validateAndSave() {
        String username = donor.getUsername();
        boolean newPasswordsNotEmpty = donorEditForm.newPasswordsNotEmpty();
        boolean newPasswordsMatch = donorEditForm.newPasswordsMatch();
        boolean newPasswordIsOldPassword = donorEditForm.newPasswordIsOldPassword();
        boolean passwordChanged = newPasswordsNotEmpty && newPasswordsMatch && !newPasswordIsOldPassword;
        Donor newDonor = donorEditForm.getDonor();

        if(donorEditForm.passwordsMatch()) {

            boolean usernameChanged = !newDonor.getUsername().equals(username);

            if(newPasswordsNotEmpty && !newPasswordsMatch){
                DialogBoxHandler.showErrorNotification("New passwords do not match");
            }
            else {
                if(newPasswordsNotEmpty && newPasswordIsOldPassword){
                    DialogBoxHandler.showErrorNotification("New password can not be old password!");
                }else {
                    Dialog dialog;
                    if (usernameChanged || passwordChanged) {
                        dialog = DialogBoxHandler.makeDialogBox("Account info and credentials edited successfully. You will be returned to the login page.");
                        if(passwordChanged) {
                            newDonor.setPassword(donorEditForm.getNewPassword());
                        }
                    } else {
                        dialog = DialogBoxHandler.makeDialogBox("Account info edited successfully!");
                    }
                    dialog.addOpenedChangeListener(e -> {
                        if (!e.isOpened()) {
                            if (usernameChanged || passwordChanged) {
                                UI.getCurrent().navigate("/login");
                            } else {
                                UI.getCurrent().navigate("/donor/locations");
                            }
                        }
                    });
                    donorService.save(newDonor);
                    this.donor=newDonor;
                }
            }
        }else{
            DialogBoxHandler.showErrorNotification("Incorrect password!");
        }
    }

    public void delete(){
        Dialog dialog = new Dialog();
        Button cancel = new Button("Cancel", e -> {
            dialog.close();
        });
        Button confirm = new Button("Confirm", e->{
            appointmentService.deleteByDonorId(donor.getId());
            donorService.deleteDonorById(donor.getId());
            UI.getCurrent().navigate("/login");
            dialog.close();
        });
        dialog.setHeaderTitle("Are you sure you want to delete your account?");
        dialog.getFooter().add(confirm);
        dialog.getFooter().add(cancel);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirm.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dialog.open();
    }
}