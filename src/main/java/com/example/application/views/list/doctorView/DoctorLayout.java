package com.example.application.views.list.doctorView;

import com.example.application.data.entity.Doctor;
import com.example.application.data.service.DoctorService;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


public class DoctorLayout extends AppLayout {
    private final SecurityService securityService;
    private Doctor doctor;
    private DoctorService doctorService;

    public DoctorLayout(SecurityService securityService, DoctorService doctorService) {
        this.securityService=securityService;
        String username= securityService.getAuthenticatedUser().getUsername();
        this.doctorService = doctorService;

        this.doctor = doctorService.findByUsername(username).get();
        createHeader();
        createDrawer();
    }

    private void createHeader() {

        H2 logo = new H2("Welcome back, "+doctor.getFirstName() + " " + doctor.getLastName() + "!");
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        header.setWidth("98%");
        header.expand(logo);
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink appointmentLink = new RouterLink("Appointments", DoctorAppointmentView.class);
        appointmentLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(appointmentLink));
    }
}