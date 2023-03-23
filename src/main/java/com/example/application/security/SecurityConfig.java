package com.example.application.security;

import com.example.application.data.service.SecurityUserDetailsService;
import com.example.application.views.list.mainView.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity 
@Configuration
public class SecurityConfig extends VaadinWebSecurity { 

    /**
     * Demo SimpleInMemoryUserDetailsManager, which only provides
     * two hardcoded in-memory users and their roles.
     * NOTE: This shouldn't be used in real-world applications.
     */
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.formLogin(formLogin -> formLogin
                .successHandler(new CustomAuthenticationSuccessHandler()));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(securityUserDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }
}