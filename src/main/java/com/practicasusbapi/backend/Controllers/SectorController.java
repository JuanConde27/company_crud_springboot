package com.practicasusbapi.backend.Controllers;

import com.practicasusbapi.backend.Models.CompanyModel;
import com.practicasusbapi.backend.Models.SectorModel;
import com.practicasusbapi.backend.Security.JwtUtils;
import com.practicasusbapi.backend.Services.SectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/sector")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SectorController {

    private final
    SectorService sectorService;

    private final
    JwtUtils jwtUtils;

    public SectorController(SectorService sectorService, JwtUtils jwtUtils) {
        this.sectorService = sectorService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todos los sectores", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron todos los sectores", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo obtener los sectores", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> findAll(@RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.sectorService.findAll();
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Obtener un sector por su id", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo el sector", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo obtener el sector", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> findById (Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.sectorService.findById(id);
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find/{primarySector}")
    @Operation(summary = "Obtener un sector por su sector primario", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo el sector", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo obtener el sector", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> findByPrimarySector(String primarySector, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.sectorService.findByPrimarySector(primarySector);
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find/{secondarySector}")
    @Operation(summary = "Obtener un sector por su sector secundario", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo el sector", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo obtener el sector", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> findBySecondarySector(String secondarySector, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.sectorService.findBySecondarySector(secondarySector);
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/{id}")
    @Operation(summary = "Guardar un sector", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se guardo el sector", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo guardar el sector", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> save(@PathVariable("id") Long id, @RequestBody SectorModel sector) {
        CompanyModel company = new CompanyModel();
        company.setId(id);
        sector.setCompany(company);
        return this.sectorService.save(id, sector);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar un sector", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo el sector", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo actualizar el sector", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> updateById(@PathVariable("id") Long id, @RequestBody SectorModel sector, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.sectorService.updateById(id, sector);
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar un sector", tags = {"Sector"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimino el sector", content = @Content(schema = @Schema(implementation = SectorModel.class))),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar el sector", content = @Content(schema = @Schema(implementation = SectorModel.class)))
    })
    public ResponseEntity<SectorModel> deleteById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return this.sectorService.deleteById(id);
        } else {
            System.out.println("No se pudo validar el token");
            return ResponseEntity.badRequest().build();
        }
    }
}
