package com.practicasusbapi.backend.Repositories;

import com.practicasusbapi.backend.Models.SectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectorRepository extends JpaRepository<SectorModel, Long> {
    SectorModel findByPrimarySector(String primarySector);
    SectorModel findBySecondarySector(String secondarySector);
}
