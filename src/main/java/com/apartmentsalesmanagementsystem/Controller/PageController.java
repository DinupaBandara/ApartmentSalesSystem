package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Entity.Client;
import com.apartmentsalesmanagementsystem.Repository.AdminRepository;
import com.apartmentsalesmanagementsystem.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AdminRepository adminRepository;
    private final AdminService adminService;

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

    // Handle Admin Profile Form Submission (Save Changes)
    @PostMapping("/admin/profile/update")
    public String updateAdminProfile(@ModelAttribute Admin updatedAdmin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (currentAdmin == null) {
            return "redirect:/auth";
        }

        // Update name and role info in the database using the service
        adminService.updateAdminInfo(currentAdmin.getId(), updatedAdmin.getFullName(), currentAdmin.getRole());

        // Refresh session attributes so the changes display instantly across the navbar/header
        currentAdmin.setFullName(updatedAdmin.getFullName());
        session.setAttribute("loggedInAdmin", currentAdmin);

        return "redirect:/admin/profile?success=true";
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

        return "Admin/admin-management";
    }
}