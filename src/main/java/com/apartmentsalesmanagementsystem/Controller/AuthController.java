package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Client;
import com.apartmentsalesmanagementsystem.Service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private ClientService clientService;

    // Show the Login/Register Page
    @GetMapping("/auth")
    public String showAuthPage(Model model) {
        model.addAttribute("client", new Client());
        return "auth"; // Corresponds to auth.html
    }

    // Handle Registration Submission
    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client) {
        clientService.registerClient(client);
        return "redirect:/auth?registered=true";
    }

    // Handle Login Submission
    @PostMapping("/login")
    public String loginClient(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Client client = clientService.authenticateClient(email, password);

        if (client != null) {
            // Store client in session on successful login
            session.setAttribute("loggedInClient", client);
            return "redirect:/dashboard";
        } else {
            return "redirect:/auth?error=true";
        }
    }

    // Show Client Dashboard (Only accessible if logged in)
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Client client = (Client) session.getAttribute("loggedInClient");

        // Redirect to login if user is not in session
        if (client == null) {
            return "redirect:/auth";
        }

        model.addAttribute("client", client);
        return "dashboard"; // Corresponds to dashboard.html
    }

    // Show Client Profile Page (Only accessible if logged in)
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Client client = (Client) session.getAttribute("loggedInClient");

        // Redirect to login if user is not in session
        if (client == null) {
            return "redirect:/auth";
        }

        model.addAttribute("client", client);
        return "profile"; // Corresponds to profile.html
    }
    // Handle Profile Update Submission
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Client updatedClient, HttpSession session) {
        Client sessionClient = (Client) session.getAttribute("loggedInClient");

        // Redirect to login if user is not in session
        if (sessionClient == null) {
            return "redirect:/auth";
        }

        // Update fields (keeping email secure and unchanged if it's read-only)
        sessionClient.setFullName(updatedClient.getFullName());
        sessionClient.setPhone(updatedClient.getPhone());

        // If your Client entity has a password update feature, you can handle it here or keep it unchanged.
        // Save updates to database using your service
        clientService.updateClient(sessionClient);

        // Update the session attribute with the new details
        session.setAttribute("loggedInClient", sessionClient);

        return "redirect:/profile?success=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth";
    }
}