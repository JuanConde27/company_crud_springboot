package com.practicasusbapi.backend.Services;

import com.practicasusbapi.backend.Models.RecruiterModel;
import com.practicasusbapi.backend.Repositories.IRecruiterRepository;
import com.practicasusbapi.backend.Utils.ErrorResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("ALL")
@Service
public class RecruiterService {

    final
    IRecruiterRepository recruiterRepository;

    public RecruiterService(IRecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    //FIND ALL
    public ResponseEntity<ArrayList<RecruiterModel>> findAll() {
        ArrayList<RecruiterModel> recruiters = (ArrayList<RecruiterModel>) this.recruiterRepository.findAll();
        if (recruiters.size() > 0) {
            return new ResponseEntity<>(recruiters, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorResponse("No hay reclutadores registrados"), HttpStatus.NOT_FOUND);
        }
    }

    //FIND BY ID
    public ResponseEntity<RecruiterModel> findById(Long id) {
        RecruiterModel recruiterFound = this.recruiterRepository.findById(id).orElse(null);
        if (recruiterFound != null) {
            return new ResponseEntity<>(recruiterFound, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorResponse("No se encontró el reclutador"), HttpStatus.NOT_FOUND);
        }
    }

    //SAVE BY ID
    public ResponseEntity<RecruiterModel> save(Long id, RecruiterModel recruiter) {
        try {
            RecruiterModel recruiterFound = this.recruiterRepository.findByEmail(recruiter.getEmail());
            if (recruiterFound != null) {
                return new ResponseEntity(new ErrorResponse("Ya existe un reclutador con ese correo"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(this.recruiterRepository.save(recruiter), HttpStatus.OK);
        } catch (Exception error) {
            System.out.println(error);
            return new ResponseEntity(new ErrorResponse("Error al registrar el reclutador"), HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE BY ID
    public ResponseEntity<RecruiterModel> updateById(Long id, RecruiterModel recruiter) {
        RecruiterModel recruiterFound = this.recruiterRepository.findById(id).orElse(null);
        if (recruiterFound != null) {
            recruiterFound.setName(recruiter.getName());
            recruiterFound.setEmail(recruiter.getEmail());
            recruiterFound.setPhone(recruiter.getPhone());
            recruiterFound.setCompany(recruiter.getCompany());
            recruiterFound.setRole(recruiter.getRole());
            return new ResponseEntity<>(this.recruiterRepository.save(recruiterFound), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorResponse("No se encontró el reclutador"), HttpStatus.NOT_FOUND);
        }
    }

    //DELETE BY ID
    public boolean deleteById(Long id) {
        try {
            this.recruiterRepository.deleteById(id);
            return true;
        } catch (Exception error) {
            return false;
        }
    }
}
