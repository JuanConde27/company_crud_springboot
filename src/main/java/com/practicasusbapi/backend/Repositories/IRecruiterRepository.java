package com.practicasusbapi.backend.Repositories;

import com.practicasusbapi.backend.Models.RecruiterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecruiterRepository extends JpaRepository<RecruiterModel, Long> {
    RecruiterModel findByEmail(String email);
}
