package com.example.application.views.list.adminView;


import com.example.application.data.entity.Donor;
import com.example.application.data.service.DonorService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin/donors", layout = AdminLayout.class)
public class DonorListView extends VerticalLayout {
    private Grid<Donor> grid = new Grid<>(Donor.class);
    private TextField filterText = new TextField();

    private DonorService donorService;

    public DonorListView(DonorService donorService) {
        this.donorService = donorService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("username", "password", "firstName", "lastName", "email");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(donorService.findAllDonorsByName(filterText.getValue()));
    }

}