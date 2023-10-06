package com.practicasusbapi.backend.Services;

import com.practicasusbapi.backend.Models.CompanyModel;
import com.practicasusbapi.backend.Utils.ErrorResponse;
import com.practicasusbapi.backend.Repositories.ICompanyRepository;
import com.practicasusbapi.backend.Security.Bcrypt;
import com.practicasusbapi.backend.Security.JwtUtils;
import com.practicasusbapi.backend.Utils.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("ALL")
@Service
public class CompanyService {

    final
    ICompanyRepository companyRepository;

    final
    EmailService emailService;

    final
    Bcrypt bcrypt;

    final
    JwtUtils jwtUtils;

    public CompanyService(ICompanyRepository companyRepository, EmailService emailService, Bcrypt bcrypt, JwtUtils jwtUtils) {
        this.companyRepository = companyRepository;
        this.emailService = emailService;
        this.bcrypt = bcrypt;
        this.jwtUtils = jwtUtils;
    }

    //FIND ALL
    public ResponseEntity<ArrayList<CompanyModel>> findAll() {
        try {
            ArrayList<CompanyModel> companies = (ArrayList<CompanyModel>) this.companyRepository.findAll();
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al obtener las empresas"), HttpStatus.BAD_REQUEST);
        }
    }

    //REGISTER
    public ResponseEntity<CompanyModel> register(CompanyModel company) {
        try {
            CompanyModel companyFound = this.companyRepository.findByNit(company.getNit());
            if (companyFound != null) {
                return new ResponseEntity(new ErrorResponse("Ya existe una empresa con ese NIT"), HttpStatus.BAD_REQUEST);
            }
            String password = company.getPassword();
            String passwordBcrypt = bcrypt.passwordEncoder().encode(password);
            company.setPassword(passwordBcrypt);
            emailService.sendEmail(company.getEmail(), "Solicitud enviada USBC", "Hola " + company.getName() + ",\n\n" +
                    "Tu solicitud ha sido enviada, en breve nos pondremos en contacto contigo.\n\n" +
                    "Saludos,\n" +
                    "Equipo USBC");
            return new ResponseEntity<>(this.companyRepository.save(company), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al registrar la empresa"), HttpStatus.BAD_REQUEST);
        }
    }

    //LOGIN
    public ResponseEntity<CompanyModel> login(String email, String password) {
        CompanyModel company = this.companyRepository.findByEmail(email);
        if (company != null) {
            boolean ok = bcrypt.passwordEncoder().matches(password, company.getPassword());
            if (ok) {
                String token = jwtUtils.generateJwtToken(company.getId());
                company.setToken(token);
                return new ResponseEntity<>(company, HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("Email o contraseña incorrectos"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(new ErrorResponse("Email o contraseña incorrectos"), HttpStatus.BAD_REQUEST);
        }
    }

    //FIND BY ID
    public ResponseEntity<CompanyModel> findById(Long id) {
        try {
            CompanyModel company = this.companyRepository.findById(id).orElse(null);
            if (company != null) {
                return new ResponseEntity<>(company, HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("No existe una empresa con ese ID"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al obtener la empresa"), HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE BY ID
    public ResponseEntity<CompanyModel> updateById(Long id, CompanyModel company) {
        try {
            CompanyModel companyToUpdate = this.companyRepository.findById(id).orElse(null);
            if (companyToUpdate != null) {
                companyToUpdate.setName(company.getName());
                companyToUpdate.setEmail(company.getEmail());
                companyToUpdate.setPassword(company.getPassword());
                companyToUpdate.setLocation(company.getLocation());
                return new ResponseEntity<>(this.companyRepository.save(companyToUpdate), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("No existe una empresa con ese ID"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al actualizar la empresa"), HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE BY ID
    public Boolean deleteById(Long id) {
        try {
            this.companyRepository.deleteById(id);
            return true;
        } catch (Exception error) {
            return false;
        }
    }

    //ACTIVE STATUS BY ID TRUE
    public ResponseEntity<CompanyModel> activeStatusByIdTrue(Long id) {
        try {
            CompanyModel companyToUpdate = this.companyRepository.findById(id).orElse(null);
            if (companyToUpdate != null) {
                companyToUpdate.setActive(true);
                emailService.sendEmail(companyToUpdate.getEmail(), "Activación de cuenta", "Hola " + companyToUpdate.getName() + ",\n\n" +
                        "Tu cuenta ha sido activada y tu solicitud ha sido aprobada. Ahora puedes publicar tus ofertas de pasantías y empleos.\n\nPara ingresar a tu cuenta, haz click en el siguiente enlace: http://localhost:4200/login\n\n" +
                        "Saludos\n\nNo olvides visitar nuestro sitio web: https://practicasusb.com.ve/ \n\n" +
                        "Equipo de Practicas USBC");
                return new ResponseEntity<>(this.companyRepository.save(companyToUpdate), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("No existe una empresa con ese ID"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al actualizar la empresa"), HttpStatus.BAD_REQUEST);
        }
    }

    //ACTIVE STATUS BY ID FALSE
    public ResponseEntity<CompanyModel> activeStatusByIdFalse(Long id) {
        try {
            CompanyModel companyToUpdate = this.companyRepository.findById(id).orElse(null);
            if (companyToUpdate != null) {
                companyToUpdate.setActive(false);
                return new ResponseEntity<>(this.companyRepository.save(companyToUpdate), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("No existe una empresa con ese ID"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al actualizar la empresa"), HttpStatus.BAD_REQUEST);
        }
    }

    //FIND BY NIT
    public ResponseEntity<CompanyModel> findByNit(String nit) {
        try {
            CompanyModel company = this.companyRepository.findByNit(nit);
            if (company != null) {
                return new ResponseEntity<>(company, HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("No existe una empresa con ese NIT"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception error) {
            return new ResponseEntity(new ErrorResponse("Error al obtener la empresa"), HttpStatus.BAD_REQUEST);
        }
    }

    //FORGOT PASSWORD
    public boolean forgotPassword(String email) throws MessagingException {
        CompanyModel company = this.companyRepository.findByEmail(email);
        if (company != null) {
            String token = jwtUtils.generateJwtToken(company.getId());
            String url = "http://localhost:4200/reset-password/" + token;
            String Message = "Hola " + company.getName() + ",\n\n" +
                    "Para restablecer tu contraseña, haz click en el siguiente enlace:\n\n" +
                    url + "\n\n" +
                    "Si no has solicitado restablecer tu contraseña, ignora este correo.\n\n" +
                    "Saludos,\n" +
                    "El equipo de Practicas USB";
            emailService.sendEmail(email, "Restablecer contraseña", Message);
            return new ResponseEntity<>(true, HttpStatus.OK).hasBody();
        } else {
            ErrorResponse errorResponse = new ErrorResponse("El email no está registrado");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST).hasBody();
        }
    }

    //RESET PASSWORD
    public boolean resetPassword(String token, String password) throws MessagingException {
        Long id = jwtUtils.getIdFromJwtToken(token);
        CompanyModel company = this.companyRepository.findById(id).orElse(null);
        if (company != null) {
            String passwordBcrypt = bcrypt.passwordEncoder().encode(password);
            company.setPassword(passwordBcrypt);
            this.companyRepository.save(company);
            emailService.sendEmail(company.getEmail(), "Contraseña actualizada", "Hola " + company.getName() + ",\n\n" +
                    "Tu contraseña ha sido actualizada exitosamente.\n\n" +
                    "Saludos,\n" +
                    "El equipo de Practicas USB");
            return new ResponseEntity<>(true, HttpStatus.OK).hasBody();
        } else {
            ErrorResponse errorResponse = new ErrorResponse("El email no está registrado");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST).hasBody();
        }
    }
}
