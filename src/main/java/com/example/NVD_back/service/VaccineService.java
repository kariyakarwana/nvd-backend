package com.example.NVD_back.service;

import com.example.NVD_back.model.Vaccine;
import com.example.NVD_back.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {
    @Autowired
    private VaccineRepository repo;

    public Vaccine save(Vaccine v){
        return repo.save(v);
    }

    public List<Vaccine> getAll(){
        return repo.findAll();
    }

    // ✅ Add update method
    public Vaccine update(Long id, Vaccine vaccine) {
        Vaccine existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        existing.setName(vaccine.getName());
        existing.setTotalDoses(vaccine.getTotalDoses());
        existing.setMinAgeMonths(vaccine.getMinAgeMonths());
        existing.setMaxAgeMonths(vaccine.getMaxAgeMonths());

        return repo.save(existing);
    }

    // ✅ Add delete method
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
