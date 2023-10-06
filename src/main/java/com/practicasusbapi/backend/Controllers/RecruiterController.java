package com.practicasusbapi.backend.Controllers;

import com.practicasusbapi.backend.Models.CompanyModel;
import com.practicasusbapi.backend.Models.RecruiterModel;
import com.practicasusbapi.backend.Security.JwtUtils;
import com.practicasusbapi.backend.Services.RecruiterService;
import com.practicasusbapi.backend.Utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/recruiter")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RecruiterController {

    private final
    RecruiterService recruiterService;

    private final
    JwtUtils jwtUtils;

    public RecruiterController(RecruiterService recruiterService, JwtUtils jwtUtils) {
        this.recruiterService = recruiterService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todos los reclutadores", tags = {"Recruiter"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron todos los reclutadores", content = @Content(schema = @Schema(implementation = RecruiterModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo obtener los reclutadores", content = @Content(schema = @Schema(implementation = RecruiterModel.class)))
    })
    public ResponseEntity<ArrayList<RecruiterModel>> findAll(@RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.recruiterService.findAll();
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save/{id}")
    @Operation(summary = "Guardar un reclutador", tags = {"Recruiter"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se guardó el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo guardar el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class)))
    })
    public ResponseEntity<RecruiterModel> save(@PathVariable("id") Long id, @RequestBody RecruiterModel recruiter) {
        CompanyModel company = new CompanyModel();
        company.setId(id);
        recruiter.setCompany(company);
        return this.recruiterService.save(id, recruiter);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Obtener un reclutador por id", tags = {"Recruiter"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo obtener el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class)))
    })
    public ResponseEntity<RecruiterModel> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.recruiterService.findById(id);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar un reclutador por id", tags = {"Recruiter"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizó el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo actualizar el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class)))
    })
    public ResponseEntity<RecruiterModel> updateById(@PathVariable("id") Long id, @RequestBody RecruiterModel recruiter, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.recruiterService.updateById(id, recruiter);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar un reclutador por id", tags = {"Recruiter"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se eliminó el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar el reclutador", content = @Content(schema = @Schema(implementation = RecruiterModel.class)))
    })
    public String deleteById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            boolean ok = this.recruiterService.deleteById(id);
            if (ok) {
                return "Se eliminó el reclutador con id " + id;
            } else {
                return "No se pudo eliminar el reclutador con id " + id;
            }
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST).toString();
        }
    }
}
