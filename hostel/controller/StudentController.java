package com.example.hostel.controller;

import com.example.hostel.model.*;
import com.example.hostel.repository.UserRepository;
import com.example.hostel.service.RequestService;
import com.example.hostel.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final RequestService requestService;
    private final UserService userService;
    private final UserRepository userRepo;

    public StudentController(RequestService requestService, UserService userService, UserRepository userRepo) {
        this.requestService = requestService;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    //  Student Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails ud) {
        // Find the currently logged-in student
        User student = userRepo.findByUsername(ud.getUsername()).orElse(null);

        if (student != null) {

            model.addAttribute("requests", requestService.findByStudent(student));
            model.addAttribute("student", student);
        } else {

            model.addAttribute("requests", java.util.Collections.emptyList());
            model.addAttribute("student", null);
        }


        return "student/dashboard";
    }
    //  Book Service Form
    @GetMapping("/book")
    public String bookForm(Model model) {
        model.addAttribute("request", new ServiceRequest());
        model.addAttribute("types", ServiceType.values());
        model.addAttribute("minDate", LocalDate.now().toString());

        return "student/book";
    }

    // Submit Booking Request
    @PostMapping("/book")
    public String submitBooking(@ModelAttribute ServiceRequest request, @AuthenticationPrincipal UserDetails ud) {
        User student = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        request.setStudent(student);
        request.setStatus(RequestStatus.PENDING);
        requestService.createRequest(request);
        return "redirect:/student/requests";
    }

    //  View Student Requests
    @GetMapping("/requests")
    public String myRequests(Model model, @AuthenticationPrincipal UserDetails ud) {
        User student = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        model.addAttribute("requests", requestService.findByStudent(student));

        return "student/requests";
    }


}
