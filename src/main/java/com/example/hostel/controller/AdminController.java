package com.example.hostel.controller;

import com.example.hostel.model.RequestStatus;
import com.example.hostel.service.RequestService;
import com.example.hostel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RequestService requestService;
    private final UserService userService;

    public AdminController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("requests", requestService.findAll());
        model.addAttribute("cleaners", userService.findAllCleaners());
        model.addAttribute("statuses", RequestStatus.values());

        return "admin/dashboard";
    }

    @PostMapping("/assign")
    public String assignCleaner(@RequestParam Long requestId, @RequestParam Long cleanerId) {
        requestService.assignCleaner(requestId, cleanerId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/status")
    public String changeStatus(@RequestParam Long requestId, @RequestParam RequestStatus status) {
        requestService.updateStatus(requestId, status);
        return "redirect:/admin/dashboard";
    }
}
