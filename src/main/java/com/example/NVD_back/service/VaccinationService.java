package com.example.NVD_back.service;

import com.example.NVD_back.model.Role;
import com.example.NVD_back.model.User;
import com.example.NVD_back.model.VaccinationRecord;
import com.example.NVD_back.model.Vaccine;
import com.example.NVD_back.repository.UserRepository;
import com.example.NVD_back.repository.VaccineRecordRepository;
import com.example.NVD_back.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationService {
    @Autowired
    private VaccineRecordRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VaccineRepository vaccineRepo;

    public VaccinationRecord save(String nic, VaccinationRecord record){
        // Get current logged-in user (health worker)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User healthWorker = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User citizen = userRepo.findByNic(nic)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        Vaccine vaccine = vaccineRepo.findById(record.getVaccine().getId())
                .orElseThrow(() -> new RuntimeException("Invalid Vaccine"));

        if(record.getDoseNumber() > vaccine.getTotalDoses())
            throw new RuntimeException("Dose exceeds allowed limit");

        record.setCitizen(citizen);
        record.setVaccine(vaccine);

        // ✅ Auto-capture health worker info
        record.setHealthWorker(healthWorker);
        record.setProviderName(healthWorker.getFullname());
        record.setProviderTitle(healthWorker.getTitle() != null ? healthWorker.getTitle() : "Health Worker");

        return repo.save(record);
    }

    public List<VaccinationRecord> getByNic(String nic){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getRole()==Role.CITIZEN && !user.getNic().equals(nic))
            throw new AccessDeniedException("Unauthorized access");

        return repo.findByCitizenNic(nic);
    }
}
