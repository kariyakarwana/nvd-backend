package com.example.NVD_back.service;

import com.example.NVD_back.model.User;
import com.example.NVD_back.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CitizenService {

    private UserRepository repo;

    public CitizenService(UserRepository repo){
        this.repo = repo;
    }

    public User searchByNic(String nic){
        return repo.findByNic(nic)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));
    }
}
