package com.example.application.views.list.adminView;


import com.example.application.data.entity.Doctor;
import com.example.application.data.service.DoctorService;
import com.example.application.data.service.DonationCenterService;
import com.example.application.views.list.DialogBoxHandler;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin/doctors", layout = AdminLayout.class)
public class DoctorListView extends VerticalLayout {
    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    private TextField filterText = new TextField();

    private DoctorForm doctorForm;

    //services
    private DoctorService doctorService;
    private DonationCenterService donationCenterService;

    public DoctorListView(DoctorService doctorService, DonationCenterService donationCenterService) {
        this.doctorService = doctorService;
        this.donationCenterService=donationCenterService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private Component getContent() {
        SplitLayout content = new SplitLayout(grid, doctorForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        doctorForm = new DoctorForm(donationCenterService.findAll());

        //add listeners
        doctorForm.addSaveListener(e ->validateAndSave());
        doctorForm.addDeleteListener(e -> deleteDoctor());
        doctorForm.addCloseListener(e -> closeEditor());

        doctorForm.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("username", "password","firstName", "lastName","CNP","email");
        grid.addColumn(e->e.getDonationCenter().getNameAndAddress()).setHeader("Donation Center");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editDoctor(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPersonButton = new Button("Add doctor");
        addPersonButton.addClickListener(click -> addDoctor());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPersonButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    //form config
    public void editDoctor(Doctor doctor) {
        if (doctor == null) {
            closeEditor();
        } else {
            doctorForm.setDoctor(doctor);
            doctorForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void addDoctor() {
        grid.asSingleSelect().clear();
        editDoctor(new Doctor());
    }

    private void updateList() {
        grid.setItems(doctorService.findAllDoctorsByName(filterText.getValue()));
    }

    //close listener
    private void closeEditor() {
        doctorForm.setDoctor(null);
        doctorForm.setVisible(false);
        removeClassName("editing");
    }

    //save listener
    private void validateAndSave() {
        Doctor doctor = doctorForm.getDoctor();
        if(!doctorForm.donationCenterNotSet()) {
            doctorService.saveDoctor(doctor);
            DialogBoxHandler.makeDialogBox("Doctor created/edited successfully!");
            closeEditor();
            updateList();
        }else{
            DialogBoxHandler.showErrorNotification("Please assign a donation center!");
        }
    }

    //delete listener
    private void deleteDoctor(){
        Doctor doctor = doctorForm.getDoctor();
        if(doctor.getId()!=null) {
            this.doctorService.deleteDoctorById(doctor.getId());
            DialogBoxHandler.makeDialogBox("Doctor deleted succesfully!");
            closeEditor();
            updateList();
        }
    }
}