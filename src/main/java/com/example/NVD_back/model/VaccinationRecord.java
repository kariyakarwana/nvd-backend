package com.example.NVD_back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



import java.time.LocalDate;

@Entity
@Table(indexes = @Index(columnList = "citizen_id"))
@Data
public class VaccinationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"password","address","phone"})
    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private User citizen;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @JsonIgnoreProperties({"password"})
    @ManyToOne
    @JoinColumn(name = "health_worker_id")
    private User healthWorker;

    private Integer doseNumber;

    private LocalDate administrationDate;

    @NotBlank
    private String manufacturer;
    @NotBlank
    private String lotNumber;
    @NotNull
    private LocalDate expiryDate;

    @NotBlank
    private String providerName;
    private String providerOffice;
    private String providerTitle;
    @NotNull
    private LocalDate visDate;

}
