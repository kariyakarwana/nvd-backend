package com.example.NVD_back.controller;

import com.example.NVD_back.model.User;
import com.example.NVD_back.model.VaccinationRecord;
import com.example.NVD_back.repository.UserRepository;
import com.example.NVD_back.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccination")
public class VaccinationController {

    @Autowired
    private VaccinationService service;

    private UserRepository userRepo;

    @PostMapping("/{nic}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEALTH_WORKER')")
    public VaccinationRecord add(@PathVariable String nic, @RequestBody VaccinationRecord record){
        return service.save(nic, record);
    }

    @GetMapping("/{nic}")
    @PreAuthorize("hasAnyRole('ADMIN','HEALTH_WORKER','CITIZEN')")
    public List<VaccinationRecord> get(@PathVariable String nic){
        return service.getByNic(nic);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CITIZEN')")
    public List<VaccinationRecord> myRecords() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepo.findByEmail(email).orElseThrow();
        return service.getByNic(user.getNic());
    }

}
