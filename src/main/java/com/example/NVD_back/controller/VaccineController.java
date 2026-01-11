package com.example.NVD_back.controller;

import com.example.NVD_back.model.Vaccine;
import com.example.NVD_back.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    @Autowired
    private VaccineService service;

    // ✅ Add vaccine (Admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Vaccine add(@RequestBody Vaccine v) {
        return service.save(v);
    }

    // ✅ Get all vaccines
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HEALTH_WORKER')")
    public List<Vaccine> getAll(){
        return service.getAll();
    }

    // ✅ Update vaccine
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Vaccine update(@PathVariable Long id, @RequestBody Vaccine vaccine) {
        return service.update(id, vaccine);
    }

    // ✅ Delete vaccine
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


}
