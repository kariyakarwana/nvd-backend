package com.example.NVD_back.controller;

import com.example.NVD_back.model.User;
import com.example.NVD_back.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenController {
    @Autowired
    private CitizenService service;

    @GetMapping("search/{nic}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEALTH_WORKER')")
    public User searchCitizen(@PathVariable String nic){
        return service.searchByNic(nic);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEALTH_WORKER')")
    public List<User> getAllCitizens(){
        return service.getAllCitizens();
    }
}
