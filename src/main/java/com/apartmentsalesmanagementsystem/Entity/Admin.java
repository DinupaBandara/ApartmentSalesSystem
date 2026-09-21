package com.apartmentsalesmanagementsystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Email
    @Pattern(regexp = "^.*@admin\\.com$", message = "Admin email must end with @admin.com")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // e.g., "SUPER_ADMIN" or "ADMIN"
    @Column(nullable = false)
    private String role;

    // Track active or deactivated status
    @Column(nullable = false)
    private Boolean active = true;
}