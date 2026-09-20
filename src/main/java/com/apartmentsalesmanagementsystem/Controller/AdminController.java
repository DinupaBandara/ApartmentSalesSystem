package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/admins/add")
    public String addNewAdmin(@ModelAttribute Admin newAdmin, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (currentAdmin == null) {
            return "redirect:/auth";
        }

        try {
            adminService.saveAdmin(newAdmin);
            return "redirect:/admin/admin-management?success=true";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/admin-management?error=true";
        }
    }
}