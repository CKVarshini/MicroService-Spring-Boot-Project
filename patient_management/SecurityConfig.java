package com.pm.patient_management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF so your POST requests aren't blocked
            .csrf(csrf -> csrf.disable()) 
            
            .authorizeHttpRequests(auth -> auth
                // 2. Allow H2 Console
                .requestMatchers("/h2-console/**").permitAll() 
                // 3. Allow all Patient API endpoints (/patients, /patients/1, etc.)
                .requestMatchers("/patients/**","/v3/api-docs/**").permitAll() 
                .anyRequest().authenticated()
            )
            
            // 4. Required to make the H2 Console UI display correctly
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            
            // 5. Keeps the login form available if you need it
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}