package com.example.application.views.list.adminView;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;


public class AdminLayout extends AppLayout {
    private final SecurityService securityService;

    public AdminLayout(SecurityService securityService) {
        createHeader();
        createDrawer();
        this.securityService=securityService;
    }

    private void createHeader() {
        H2 logo = new H2("Welcome, Admin!");
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Log out", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("98%");
        header.expand(logo);
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink doctorListLink = new RouterLink("Doctors", DoctorListView.class);
        RouterLink donorListLink = new RouterLink("Donors", DonorListView.class);
        addToDrawer(new VerticalLayout(
                doctorListLink
        ));
        addToDrawer(new VerticalLayout(
                donorListLink
        ));
    }
}