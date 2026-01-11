package com.example.NVD_back.service;

import com.example.NVD_back.model.Role;
import com.example.NVD_back.model.User;
import com.example.NVD_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenService {

    private final UserRepository repo;

    @Autowired
    public CitizenService(UserRepository repo) {
        this.repo = repo;
    }

    public User searchByNic(String nic) {
        return repo.findByNic(nic)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));
    }

    public List<User> getAllCitizens() {
        return repo.findAll().stream()
                .filter(user -> user.getRole() == Role.CITIZEN)
                .collect(Collectors.toList());
    }
}