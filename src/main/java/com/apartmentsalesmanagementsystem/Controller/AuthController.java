package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Entity.Client;
import com.apartmentsalesmanagementsystem.Service.AdminService;
import com.apartmentsalesmanagementsystem.Service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdminService adminService;

    // Handle Registration Submission
    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client) {
        clientService.registerClient(client);
        return "redirect:/auth?registered=true";
    }

    // Handle Unified Login Submission (Admin & Client)
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {

        // 1. Route to Admin authentication if the email ends with @admin.com
        if (email.endsWith("@admin.com")) {
            Optional<Admin> adminOpt = adminService.authenticate(email, password);

            if (adminOpt.isPresent()) {
                session.setAttribute("loggedInAdmin", adminOpt.get());
                return "redirect:/admin/dashboard"; // Route to the Admin Dashboard
            } else {
                return "redirect:/auth?error=true";
            }
        }

        // 2. Otherwise, route to Client authentication
        Client client = clientService.authenticateClient(email, password);

        if (client != null) {
            // Store client in session on successful login
            session.setAttribute("loggedInClient", client);
            return "redirect:/dashboard"; // Route to the Client Dashboard
        } else {
            return "redirect:/auth?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth";
    }
}