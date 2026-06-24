package com.example.hostel.config;

import com.example.hostel.model.User;
import com.example.hostel.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Encode any existing plain-text passwords
        userRepository.findAll().forEach(user -> {
            if (!user.getPassword().startsWith("$2a$")) { // not already encoded
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                System.out.println("Encoded password for user: " + user.getUsername());
            }
        });

        //  Create default users if none exist
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(java.util.Set.of("ROLE_ADMIN"));
            userRepository.save(admin);

            User student = new User();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("student"));
            student.setRoles(java.util.Set.of("ROLE_STUDENT"));
            userRepository.save(student);

            System.out.println("Default users created (admin / student)");
        } else {
            System.out.println(" Passwords encoded and users ready.");
        }
    }
}
