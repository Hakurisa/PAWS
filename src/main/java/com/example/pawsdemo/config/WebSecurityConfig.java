package com.example.pawsdemo.config;

import com.example.pawsdemo.models.Uzivatel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //Je tady chyba, kdy se může nepřihášený uživatel dostat do index.html (overview) ale nedostane se nikam jinam
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/index").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form)-> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    /* @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Uzivatel uzivatel = this.UzivatelRepo.findByUsername(username);
            return new DetailyUzivatele(uzivatel);
        };
    } */
}