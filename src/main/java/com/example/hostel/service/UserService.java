package com.example.hostel.service;

import com.example.hostel.model.User;
import com.example.hostel.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findByUsername(username);
        if (opt.isEmpty()) throw new UsernameNotFoundException("User not found: " + username);
        User u = opt.get();


        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())

                .authorities(u.getRoles().toArray(new String[0]))
                .build();
    }

    public User register(User user, String rawRole) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(assignRole(rawRole));
        return userRepo.save(user);
    }

    private Set<String> assignRole(String role) {

        if ("admin".equalsIgnoreCase(role)) return Set.of("ROLE_ADMIN");
        if ("cleaner".equalsIgnoreCase(role)) return Set.of("ROLE_CLEANER");
        return Set.of("ROLE_STUDENT");
    }

    public List<User> findAllCleaners() {
        return userRepo.findAll().stream()
                .filter(u -> u.getRoles() != null && u.getRoles().contains("ROLE_CLEANER"))
                .collect(Collectors.toList());
    }

    public boolean exists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }
}