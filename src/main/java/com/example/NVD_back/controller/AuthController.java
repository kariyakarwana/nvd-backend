package com.example.NVD_back.controller;

import com.example.NVD_back.dto.LoginRequest;
import com.example.NVD_back.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req){
        return service.login(req.getEmail(), req.getPassword());
    }
}