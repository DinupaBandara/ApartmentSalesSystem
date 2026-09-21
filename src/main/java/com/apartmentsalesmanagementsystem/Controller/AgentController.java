package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Entity.Agent;
import com.apartmentsalesmanagementsystem.Service.AgentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/admin/agents/add")
    public String addNewAgent(@ModelAttribute Agent newAgent, HttpSession session) {
        Admin currentAdmin = (Admin) session.getAttribute("loggedInAdmin");

        // Security Check: Only a SUPER_ADMIN can provision new agents
        if (currentAdmin == null || !"SUPER_ADMIN".equals(currentAdmin.getRole())) {
            return "redirect:/auth?error=unauthorized";
        }

        try {
            agentService.saveAgent(newAgent);
            return "redirect:/admin/agent-management?success=true";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/agent-management?error=invalidDomain";
        }
    }
}