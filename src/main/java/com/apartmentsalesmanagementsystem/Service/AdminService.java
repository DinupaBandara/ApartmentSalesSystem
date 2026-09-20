package com.apartmentsalesmanagementsystem.Service;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<Admin> authenticate(String email, String password) {
        // In a real app, use PasswordEncoder to check hashed passwords
        return adminRepository.findByEmail(email)
                .filter(admin -> admin.getPassword().equals(password));
    }

    public Admin saveAdmin(Admin admin) {
        if (!admin.getEmail().endsWith("@admin.com")) {
            throw new IllegalArgumentException("Admin emails must end with @admin.com");
        }
        return adminRepository.save(admin);
    }
}