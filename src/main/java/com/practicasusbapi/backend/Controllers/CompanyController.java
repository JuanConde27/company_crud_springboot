package com.practicasusbapi.backend.Controllers;

import com.practicasusbapi.backend.Models.CompanyModel;
import com.practicasusbapi.backend.Security.JwtUtils;
import com.practicasusbapi.backend.Services.CompanyService;
import com.practicasusbapi.backend.Utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CompanyController {

    private final
    CompanyService companyService;

    private final
    JwtUtils jwtUtils;

    public CompanyController(CompanyService companyService, JwtUtils jwtUtils) {
        this.companyService = companyService;
        this.jwtUtils = jwtUtils;
    }

    //FIND ALL
    @GetMapping("/all")
    @Operation(summary = "Get all companies", description = "Get all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the companies",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<ArrayList<CompanyModel>> findAll(@RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.companyService.findAll();
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }

    //REGISTER
    @PostMapping("/register")
    @Operation(summary = "Register a company", description = "Register a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> register(@RequestBody CompanyModel company) {
        return this.companyService.register(company);
    }

    //LOGIN
    @PostMapping("/login")
    @Operation(summary = "Login a company", description = "Login a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> login(@RequestBody CompanyModel company) {
        return this.companyService.login(company.getEmail(), company.getPassword());
    }

    //FIND BY ID
    @GetMapping("/find/{id}")
    @Operation(summary = "Find a company by id", description = "Find a company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.companyService.findById(id);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE COMPANY BY ID
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a company by id", description = "Update a company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> updateById(@PathVariable("id") Long id, @RequestBody CompanyModel company, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.companyService.updateById(id, company);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE BY ID
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a company by id", description = "Delete a company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity deleteById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            boolean ok = this.companyService.deleteById(id);
            if (ok) {
                return new ResponseEntity(new ErrorResponse("Se elimino la empresa con id " + id), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("No se pudo eliminar la empresa con id " + id), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    //ACTIVE STATUS BY ID TRUE
    @PutMapping("/active/{id}")
    @Operation(summary = "Active a company by id", description = "Active a company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> activeById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.companyService.activeStatusByIdTrue(id);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    //INACTIVE STATUS BY ID FALSE
    @PutMapping("/inactive/{id}")
    @Operation(summary = "Inactive a company by id", description = "Inactive a company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> inactiveById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.companyService.activeStatusByIdFalse(id);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    //FIND BY NIT
    @GetMapping("/findnit/{nit}")
    @Operation(summary = "Find a company by nit", description = "Find a company by nit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<CompanyModel> findByNit(@PathVariable("nit") String nit, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.companyService.findByNit(nit);
        } else {
            return new ResponseEntity(new ErrorResponse("No se pudo validar el token"), HttpStatus.BAD_REQUEST);
        }
    }

    //FORGOT PASSWORD
    @PostMapping("/forgotpassword")
    @Operation(summary = "Forgot password", description = "Forgot password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public ResponseEntity<Boolean> forgotPassword(@RequestBody CompanyModel company) throws MessagingException {
        if (this.companyService.forgotPassword(company.getEmail())) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    //RESET PASSWORD
    @PutMapping("/resetpassword/{token}")
    @Operation(summary = "Reset password", description = "Reset password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the company",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyModel.class))}),
            @ApiResponse(responseCode = "400", description = "No se pudo validar el token",
                    content = @Content)})
    public  ResponseEntity<Boolean> resetPassword(@PathVariable("token") String token, @RequestBody CompanyModel company) throws MessagingException {
        if (this.companyService.resetPassword(token, company.getPassword())) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }
}
