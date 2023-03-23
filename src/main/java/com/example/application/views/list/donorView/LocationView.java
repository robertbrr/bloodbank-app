package com.example.application.views.list.donorView;

import com.example.application.data.entity.Appointment;
import com.example.application.data.entity.DonationCenter;
import com.example.application.data.entity.Donor;
import com.example.application.data.service.AppointmentService;
import com.example.application.data.service.DonationCenterService;
import com.example.application.data.service.DonorService;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "donor/locations", layout = DonorLayout.class)
@RolesAllowed("ROLE_DONOR")
public class LocationView extends VerticalLayout {

    private SecurityService securityService;
    private DonorService donorService;
    private DonationCenterService donationCenterService;
    private Donor donor;
    private AppointmentForm appointmentForm;
    private AppointmentService appointmentService;
    private TextField filterText = new TextField();

    private Grid<DonationCenter> grid = new Grid<>(DonationCenter.class);
    public LocationView(SecurityService securityService, DonorService donorService, DonationCenterService donationCenterService, AppointmentService appointmentService) {
        this.donorService = donorService;
        this.securityService=securityService;
        this.donationCenterService=donationCenterService;
        this.appointmentService=appointmentService;
        String username = securityService.getAuthenticatedUser().getUsername();
        this.donor = donorService.findDonorByUsername(username).get();
        configureGrid();
        configureForm();
        add(getContent());
        closeEditor();
        //add(new VerticalLayout(grid));

    }

    private void configureGrid() {
        grid.addClassNames("locations-grid");
        grid.setItems(donationCenterService.findAll());
        grid.removeAllColumns();
        grid.addColumn(e->e.getName()).setHeader("Name");
        grid.addColumn(e->{
            StringBuilder stringBuilder = new StringBuilder();
            int time = e.getStartHour()/100;
            if (time < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time);
            stringBuilder.append(":");
            time = e.getStartHour()%100;
            if (time < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time);
            return stringBuilder.toString();
        }).setHeader("Start Hour");
        grid.addColumn(e->{
            StringBuilder stringBuilder = new StringBuilder();
            int time = e.getEndHour()/100;
            if (time < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time);
            stringBuilder.append(":");
            time = e.getEndHour()%100;
            if (time < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time);
            return stringBuilder.toString();
        }).setHeader("End Hour");
        grid.addColumn(e->e.getAddress()).setHeader("Address");
        grid.addColumn(e->e.getArea()).setHeader("Area");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                addAppointment(event.getValue()));
    }

    private void configureForm() {
        appointmentForm = new AppointmentForm(appointmentService);
        appointmentForm.setWidth("25em");
    }

    private Component getContent() {
        SplitLayout content = new SplitLayout(grid, appointmentForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    public void addAppointment(DonationCenter donationCenter) {
        if (donationCenter == null) {
            closeEditor();
        } else {
            appointmentForm.setDonor(donor);
            appointmentForm.setAppointment(new Appointment());
            appointmentForm.setDonationCenter(donationCenter);
            appointmentForm.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        appointmentForm.setDonationCenter(null);
        appointmentForm.setVisible(false);
        removeClassName("editing");
    }

}
