package com.apartmentsalesmanagementsystem;

import com.apartmentsalesmanagementsystem.Entity.Admin;
import com.apartmentsalesmanagementsystem.Repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApartmentSalesManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartmentSalesManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedSuperAdmin(AdminRepository adminRepository) {
        return args -> {
            // Check if a super admin already exists before creating one
            if (!adminRepository.existsByRole("SUPER_ADMIN")) {
                Admin superAdmin = new Admin();
                superAdmin.setFullName("System Super Admin");
                superAdmin.setEmail("super@admin.com");
                superAdmin.setPassword("admin123");
                superAdmin.setRole("SUPER_ADMIN");

                adminRepository.save(superAdmin);
                System.out.println("Default Super Admin created: super@admin.com / admin123");
            }
        };
    }
}