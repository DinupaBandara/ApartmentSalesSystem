package com.apartmentsalesmanagementsystem.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agents")
@Data
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role = "AGENT"; // Default role

    private Boolean active = true;
}