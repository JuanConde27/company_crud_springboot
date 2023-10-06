package com.practicasusbapi.backend.Services;

import com.practicasusbapi.backend.Models.SectorModel;
import com.practicasusbapi.backend.Repositories.ISectorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
public class SectorService {

    final
    ISectorRepository sectorRepository;

    public SectorService(ISectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    //FIND BY PRIMARY SECTOR
    public ResponseEntity<SectorModel> findByPrimarySector(String primarySector) {
        try {
            SectorModel sectorFound = this.sectorRepository.findByPrimarySector(primarySector);
            if (sectorFound != null) {
                return new ResponseEntity<>(sectorFound, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //FIND BY SECONDARY SECTOR
    public ResponseEntity<SectorModel> findBySecondarySector(String secondarySector) {
        try {
            SectorModel sectorFound = this.sectorRepository.findBySecondarySector(secondarySector);
            if (sectorFound != null) {
                return new ResponseEntity<>(sectorFound, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //FIND ALL
    public ResponseEntity<SectorModel> findAll() {
        try {
            SectorModel sectorFound = (SectorModel) this.sectorRepository.findAll();
            return new ResponseEntity<>(sectorFound, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //FIND BY ID
    public ResponseEntity<SectorModel> findById(Long id) {
        try {
            SectorModel sectorFound = this.sectorRepository.findById(id).orElse(null);
            if (sectorFound != null) {
                return new ResponseEntity<>(sectorFound, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //SAVE BY ID
    public ResponseEntity<SectorModel> save(Long id, SectorModel sector) {
        try {
            SectorModel sectorFound = this.sectorRepository.findById(id).orElse(null);
            if (sectorFound == null) {
                return new ResponseEntity<>(this.sectorRepository.save(sector), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE BY ID
    public ResponseEntity<SectorModel> updateById(Long id, SectorModel sector) {
        try {
            SectorModel sectorFound = this.sectorRepository.findById(id).orElse(null);
            assert sectorFound != null;
            sectorFound.setPrimarySector(sector.getPrimarySector());
            sectorFound.setSecondarySector(sector.getSecondarySector());
            return new ResponseEntity<>(this.sectorRepository.save(sectorFound), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE BY ID
    public ResponseEntity<SectorModel> deleteById(Long id) {
        try {
            SectorModel sectorFound = this.sectorRepository.findById(id).orElse(null);
            if (sectorFound != null) {
                this.sectorRepository.deleteById(id);
                return new ResponseEntity<>(sectorFound, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
