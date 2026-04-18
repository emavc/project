package com.example.project.config;

import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("DataInitializer started");

        try {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );
                userRepository.save(admin);
                System.out.println("Admin created");
            } else {
                System.out.println("Admin already exists");
            }

            if (userRepository.findByUsername("cashier").isEmpty()) {
                User cashier = new User(
                        "cashier",
                        passwordEncoder.encode("cashier123"),
                        Role.CASHIER
                );
                userRepository.save(cashier);
                System.out.println("Cashier created");
            } else {
                System.out.println("Cashier already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}