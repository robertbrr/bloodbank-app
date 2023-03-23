package com.example.application.views.list.mainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@RouteAlias("")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();

	public LoginView(){
		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		login.setAction("login");
		Button button = new Button("Don't have an account?");
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.addClickListener(e ->
				button.getUI().ifPresent(ui ->
						ui.navigate("/register"))
		);
		login.setForgotPasswordButtonVisible(false);
		add(new H1("Welcome!"), login,button);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}