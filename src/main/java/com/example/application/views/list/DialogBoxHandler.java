package com.example.application.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class DialogBoxHandler {
    public static Dialog makeDialogBox(String title){
        Dialog dialog = new Dialog();
        Button button = new Button("Ok", e -> {
            dialog.close();
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.setHeaderTitle(title);
        dialog.getFooter().add(button);
        dialog.open();
        return dialog;
    }
    public static void showErrorNotification(String title){
        Notification notification = Notification.show(title);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
