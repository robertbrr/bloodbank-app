package com.example.application.views.list.donorView;

import com.example.application.data.entity.Donor;
import com.example.application.data.service.DonorService;
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


public class DonorLayout extends AppLayout {
    private final SecurityService securityService;
    private Donor donor;
    private DonorService donorService;

    public DonorLayout(SecurityService securityService, DonorService donorService) {

        this.securityService=securityService;
        String username= securityService.getAuthenticatedUser().getUsername();
        this.donorService = donorService;

        this.donor = donorService.findDonorByUsername(username).get();

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H2 logo = new H2("Welcome back, "+donor.getFirstName() + " " + donor.getLastName() + "!");
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Log out", e -> securityService.logout());
        Button editDetails = new Button("Edit details");
        editDetails.addClickListener(e ->
                editDetails.getUI().ifPresent(ui ->
                        ui.navigate("/donor/edit"))
        );
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,editDetails,logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        header.setWidth("98%");
        header.expand(logo);
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
       RouterLink locationsLink = new RouterLink("Locations", LocationView.class);
       locationsLink.setHighlightCondition(HighlightConditions.sameLocation());
       RouterLink appointmentLink = new RouterLink("Appointments", DonorAppointmentView.class);
       appointmentLink.setHighlightCondition(HighlightConditions.sameLocation());
       addToDrawer(new VerticalLayout(locationsLink));
       addToDrawer(new VerticalLayout(appointmentLink));
    }
}