package com.apartmentsalesmanagementsystem.Controller;

import com.apartmentsalesmanagementsystem.Entity.Client;
import com.apartmentsalesmanagementsystem.Service.ClientService; // Assuming this is your service package
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Client updatedClient, HttpSession session) {
        Client sessionClient = (Client) session.getAttribute("loggedInClient");

        if (sessionClient == null) {
            return "redirect:/auth";
        }

        sessionClient.setFullName(updatedClient.getFullName());
        sessionClient.setPhone(updatedClient.getPhone());
        sessionClient.setNic(updatedClient.getNic());

        clientService.updateClient(sessionClient);

        session.setAttribute("loggedInClient", sessionClient);
        return "redirect:/profile?success=true";
    }
}