package com.example.NVD_back.repository;

import com.example.NVD_back.model.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineRecordRepository extends JpaRepository<VaccinationRecord, Long> {
    List<VaccinationRecord> findByCitizenNic(String nic);

}
