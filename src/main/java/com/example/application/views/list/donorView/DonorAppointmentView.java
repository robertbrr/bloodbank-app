package com.example.application.views.list.donorView;

import com.example.application.data.entity.Appointment;
import com.example.application.data.entity.Donor;
import com.example.application.data.service.AppointmentService;
import com.example.application.data.service.DonorService;
import com.example.application.security.SecurityService;
import com.example.application.views.list.DialogBoxHandler;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@Route(value = "donor/appointments", layout = DonorLayout.class)
@RolesAllowed("ROLE_DONOR")
public class DonorAppointmentView extends VerticalLayout {

    private SecurityService securityService;
    private AppointmentService appointmentService;
    private Donor donor;

    private Grid<Appointment> grid = new Grid<>(Appointment.class);
    public DonorAppointmentView(SecurityService securityService, DonorService donorService, AppointmentService appointmentService) {
        this.securityService=securityService;
        this.appointmentService=appointmentService;
        String username = securityService.getAuthenticatedUser().getUsername();
        this.donor = donorService.findDonorByUsername(username).get();
        configureGrid();

        add(new VerticalLayout(grid));

    }

    private void configureGrid() {
        grid.addClassNames("locations-grid");
        grid.setItems(appointmentService.findByDonorId(this.donor.getId()));
        grid.removeAllColumns();
        grid.addColumn(e->e.getDonationCenter().getName()).setHeader("Location");
        grid.addColumn(e->e.getDonationCenter().getAddress()).setHeader("Address");
        grid.addColumn(e->e.getDonationCenter().getArea()).setHeader("Area");
        grid.addColumn(e->e.getDate().toLocalDate()).setHeader("Date");
        grid.addColumn(Appointment::getStatus).setHeader("Status");
        grid.addComponentColumn(this::deleteAppointmentButton);

    }

    public void deleteAppointmentDialog(Appointment appointment){

            Dialog dialog = new Dialog();
            Button cancel = new Button("Cancel", h -> {
                updateList();
                dialog.close();
            });
            Button confirm = new Button("Confirm", h -> {
                if(appointment.getDate().toLocalDate().isBefore(LocalDate.now())) {
                    DialogBoxHandler.makeDialogBox("Can't delete past appointments.");
                }else{
                    appointmentService.deleteById(appointment.getId());
                    updateList();
                }
                dialog.close();
            });
            dialog.getFooter().add(confirm);
            dialog.getFooter().add(cancel);
            cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            dialog.setHeaderTitle("Are you sure you want to delete this appointment?");
            dialog.open();
    }

    public Button deleteAppointmentButton(Appointment appointment){
        {
            Button button = new Button("Delete");
            button.addClickListener(e-> deleteAppointmentDialog(appointment));
            return button;
        }
    }

    private void updateList() {
        grid.setItems(appointmentService.findByDonorId(this.donor.getId()));
    }

}
