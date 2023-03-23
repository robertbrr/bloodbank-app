package com.example.application.views.list.doctorView;

import com.example.application.data.entity.Appointment;
import com.example.application.data.entity.Doctor;
import com.example.application.data.service.AppointmentService;
import com.example.application.data.service.DoctorService;
import com.example.application.security.SecurityService;
import com.example.application.views.list.DialogBoxHandler;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "doctor/appointments", layout = DoctorLayout.class)
@RolesAllowed("ROLE_DOCTOR")
public class DoctorAppointmentView extends VerticalLayout {

    private Grid<Appointment> grid = new Grid<>(Appointment.class);

    private AppointmentService appointmentService;
    private Doctor doctor;

    public DoctorAppointmentView(DoctorService doctorService, SecurityService securityService, AppointmentService appointmentService) {
        this.appointmentService=appointmentService;

        String username = securityService.getAuthenticatedUser().getUsername();
        this.doctor = doctorService.findByUsername(username).get();

        configureGrid();
        add(new VerticalLayout(grid));

    }

    private void configureGrid() {
        grid.addClassNames("locations-grid");
        grid.setItems(appointmentService.findByDonationCenter_Id(doctor.getDonationCenter().getId()));
        grid.removeAllColumns();
        grid.addColumn(e->e.getDonationCenter().getName()).setHeader("Location");
        grid.addColumn(e->e.getDonationCenter().getAddress()).setHeader("Address");
        grid.addColumn(e->e.getDonationCenter().getArea()).setHeader("Area");
        grid.addColumn(e->e.getDate().toLocalDate()).setHeader("Date");
        grid.addColumn(e-> e.getDonor().getFirstName() + " " + e.getDonor().getLastName()).setHeader("Donor");
        grid.addColumn(Appointment::getStatus).setHeader("Status");
        grid.addComponentColumn(this::confirmAppointmentButton);
    }

    private void confirmAppointmentDialog(Appointment appointment){
        Dialog dialog = new Dialog();
        Button cancel = new Button("Cancel");
        Button confirm = new Button("Confirm");

        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancel.addClickListener(e -> {
            updateList();
            dialog.close();
        });

        confirm.addClickListener(e -> {
            appointment.setStatus("CONFIRMED");
            appointmentService.saveAppointment(appointment);
            updateList();
            dialog.close();
        });

        dialog.getFooter().add(confirm);
        dialog.getFooter().add(cancel);

        dialog.setHeaderTitle("Are you sure you want to confirm this appointment?");
        dialog.open();
    }

    private Button confirmAppointmentButton(Appointment appointment){
        Button button = new Button("Confirm");
        button.addClickListener(f-> {
            if(!appointment.getStatus().equals("CONFIRMED")) {
                confirmAppointmentDialog(appointment);
            }
            else
            {
                DialogBoxHandler.showErrorNotification("Already confirmed.");
            }
        });
        return button;

    }

    private void updateList() {
        grid.setItems(appointmentService.findByDonationCenter_Id(this.doctor.getDonationCenter().getId()));
    }

}
