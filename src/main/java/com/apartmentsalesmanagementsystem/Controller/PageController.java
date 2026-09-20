package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Entity.Client;
import com.apartmentsalesmanagementsystem.Repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    // Show the Login/Register Page
    @GetMapping("/auth")
    public String showAuthPage(Model model) {
        model.addAttribute("client", new Client());
        return "auth";
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Client client = (Client) session.getAttribute("loggedInClient");

        // Redirect to login if user is not in session
        if (client == null) {
            return "redirect:/auth";
        }

        model.addAttribute("activeTab", "dashboard");
        model.addAttribute("client", client);
        return "client/dashboard";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Client client = (Client) session.getAttribute("loggedInClient");

        // Redirect to login if user is not in session
        if (client == null) {
            return "redirect:/auth";
        }

        model.addAttribute("activeTab", "profile");
        model.addAttribute("client", client);
        return "client/profile";
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        // Secure the route: verify an admin is in the session
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        if (admin == null) {
            return "redirect:/auth";
        }

        // Pass the active tab to highlight the sidebar link
        model.addAttribute("activeTab", "dashboard");
        return "Admin/admin-dashboard";
    }

    @GetMapping("/admin/profile")
    public String showAdminProfile(HttpSession session, Model model) {
        // Secure the route: verify an admin is in the session
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        if (admin == null) {
            return "redirect:/auth";
        }

        // Pass the active tab and the admin object for form binding
        model.addAttribute("activeTab", "profile");
        model.addAttribute("admin", admin);
        return "Admin/admin-profile";
    }

    @GetMapping("/admin/admin-management")
    public String showAdminsList(HttpSession session, Model model) {
        // Secure the route
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        if (admin == null) {
            return "redirect:/auth";
        }

        model.addAttribute("activeTab", "admins");
        // Fetch all admins directly from the repository
        model.addAttribute("adminsList", adminRepository.findAll());
        model.addAttribute("newAdmin", new Admin());

        return "admin/admin-management";
    }
}