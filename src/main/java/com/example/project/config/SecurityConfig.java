package com.example.project.config;

import com.example.project.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomLoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          CustomLoginSuccessHandler loginSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/dasboard.html").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/edit-product.html", "/restock.html").hasRole("ADMIN")
                        .requestMatchers("/sales.html", "/stock.html", "/product.html").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/api/products/add/**").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/api/products/update/**").hasRole("ADMIN")
                        .requestMatchers("/api/products/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/stock/restock/**").hasRole("ADMIN")
                        .requestMatchers("/api/products/all").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/api/stock/products").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("product.html").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/api/stock/search").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/users.html").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/sales/**").hasAnyRole("ADMIN", "CASHIER")
                        .requestMatchers("/api/auth/me").hasAnyRole("ADMIN", "CASHIER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/perform_login")
                        .successHandler(loginSuccessHandler)
                        .failureUrl("/login.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/login.html?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}