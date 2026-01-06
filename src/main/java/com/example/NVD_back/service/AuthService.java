package com.example.NVD_back.service;

import com.example.NVD_back.model.User;
import com.example.NVD_back.repository.UserRepository;
import com.example.NVD_back.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder encoder;
    public String login(String email, String password){
        User user = repo.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Email not found"));

        if(!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}
