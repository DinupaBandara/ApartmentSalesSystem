package com.apartmentsalesmanagementsystem.Service;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<Admin> authenticate(String email, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email)
                .filter(admin -> admin.getPassword().equals(password));

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getActive() != null && !admin.getActive()) {
                return Optional.empty();
            }
            return Optional.of(admin);
        }
        return Optional.empty();
    }

    public Admin saveAdmin(Admin admin) {
        if (!admin.getEmail().endsWith("@admin.com")) {
            throw new IllegalArgumentException("Admin emails must end with @admin.com");
        }
        if (admin.getActive() == null) {
            admin.setActive(true);
        }
        return adminRepository.save(admin);
    }

    public void updateAdminInfo(Long id, String newFullName, String newRole) {
        Admin admin = adminRepository.findById(id).orElse(null);

        if (admin != null) {
            admin.setFullName(newFullName);
            admin.setRole(newRole);
            adminRepository.save(admin);
        }
    }

    public boolean toggleAdminStatus(Long id, Admin currentLoggedInAdmin) {
        if (currentLoggedInAdmin == null || !"SUPER_ADMIN".equals(currentLoggedInAdmin.getRole())) {
            return false;
        }

        Admin targetAdmin = adminRepository.findById(id).orElse(null);
        if (targetAdmin != null) {
            if (targetAdmin.getId().equals(currentLoggedInAdmin.getId())) {
                return false;
            }

            boolean currentStatus = targetAdmin.getActive() != null ? targetAdmin.getActive() : true;
            targetAdmin.setActive(!currentStatus);
            adminRepository.save(targetAdmin);
            return true;
        }
        return false;
    }
}