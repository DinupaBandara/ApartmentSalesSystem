package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/admins/add")
    public String addNewAdmin(@ModelAttribute Admin newAdmin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (currentAdmin == null || !"SUPER_ADMIN".equals(currentAdmin.getRole())) {
            return "redirect:/auth";
        }

        try {
            adminService.saveAdmin(newAdmin);
            return "redirect:/admin/admin-management?success=true";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/admin-management?error=true";
        }
    }

    @PostMapping("/admin/admins/update")
    public String updateAdminDetails(@ModelAttribute Admin updatedAdmin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("loggedInAdmin");

        if (currentAdmin == null || !"SUPER_ADMIN".equals(currentAdmin.getRole())) {
            return "redirect:/auth";
        }

        adminService.updateAdminInfo(updatedAdmin.getId(), updatedAdmin.getFullName(), updatedAdmin.getRole());

        return "redirect:/admin/admin-management?success=true";
    }

    @PostMapping("/admin/admins/toggle-status/{id}")
    public String toggleAdminStatus(@PathVariable Long id, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("loggedInAdmin");

        if (currentAdmin == null || !"SUPER_ADMIN".equals(currentAdmin.getRole())) {
            return "redirect:/auth?error=unauthorized";
        }

        boolean success = adminService.toggleAdminStatus(id, currentAdmin);

        if (!success) {
            return "redirect:/admin/admin-management?error=actionFailed";
        }

        return "redirect:/admin/admin-management?success=true";
    }
}