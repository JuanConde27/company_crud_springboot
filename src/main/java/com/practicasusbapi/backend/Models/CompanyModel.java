package com.practicasusbapi.backend.Models;

import jakarta.persistence.*;

import java.util.List;

@SuppressWarnings("ALL")
@Entity
@Table(name = "companies")
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "nit", nullable = false, length = 100)
    private String nit;

    @Column(name = "active", nullable = false, length = 100)
    private boolean active;

    @Transient
    private String token;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<RecruiterModel> recluters;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private SectorModel sector;

    public CompanyModel() {
    }

    public CompanyModel(Long id, String name, String email, String password, String location, String nit, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.nit = nit;
        this.active = active;
    }

    public CompanyModel(String name, String email, String password, String location, String nit, boolean active) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.nit = nit;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RecruiterModel> getRecluters() {
        return recluters;
    }

    public void setRecluters(List<RecruiterModel> recluters) {
        this.recluters = recluters;
    }

    public SectorModel getSector() {
        return sector;
    }

    public void setSector(SectorModel sector) {
        this.sector = sector;
    }

}
