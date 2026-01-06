package com.example.NVD_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // ---- Citizen Details ----
    private String fullname;

    @Column(unique = true)
    private String nic;

    private String address;
    private String phone;
    private LocalDate dob;
}
