package com.practicasusbapi.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@SuppressWarnings("ALL")
@Entity
@Table(name = "sectors")
public class SectorModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primary_sector", nullable = false, length = 100)
    private String primarySector;

    @Column(name = "secondary_sector", nullable = false, length = 100)
    private String secondarySector;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyModel company;

    public SectorModel() {
    }

    public SectorModel(Long id, String primarySector, String secondarySector, CompanyModel company) {
        this.id = id;
        this.primarySector = primarySector;
        this.secondarySector = secondarySector;
        this.company = company;
    }

    public SectorModel(String primarySector, String secondarySector, CompanyModel company) {
        this.primarySector = primarySector;
        this.secondarySector = secondarySector;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getPrimarySector() {
        return primarySector;
    }

    public String getSecondarySector() {
        return secondarySector;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrimarySector(String primarySector) {
        this.primarySector = primarySector;
    }

    public void setSecondarySector(String secondarySector) {
        this.secondarySector = secondarySector;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }
}
