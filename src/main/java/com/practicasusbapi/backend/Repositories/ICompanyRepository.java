package com.practicasusbapi.backend.Repositories;

import com.practicasusbapi.backend.Models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<CompanyModel, Long> {
    CompanyModel findByEmail(String email);
    CompanyModel findByNit(String nit);
}

