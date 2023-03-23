package com.example.security.full.security.app.controller;

import com.example.security.full.security.app.model.Ciudad;
import com.example.security.full.security.app.payload.request.CiudadRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/ciudades"))

public class CiudadController {

    private ReservaService reservaService;

    public CiudadController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Ciudad> saveCiudad(@RequestBody CiudadRequest ciudadRequest){
        ;return new ResponseEntity<Ciudad>(reservaService.saveCiudad(ciudadRequest), HttpStatus.CREATED);
    };
    @GetMapping("/")

    public List<Ciudad> getAllCiudades(){
        return reservaService.getAllCiudades();
    }

    @GetMapping("{id}")

    public ResponseEntity<Ciudad> getCiudadById(@PathVariable("id") long ciudadId){
        return new ResponseEntity<Ciudad>(reservaService.getCiudadById(ciudadId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Ciudad> updateCiudad(@PathVariable("id") long ciudadId, @RequestBody CiudadRequest ciudadRequest){
        return new ResponseEntity<Ciudad>(reservaService.updateCiudad(ciudadRequest, ciudadId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<String> deleteCiudadBy(@PathVariable("id") long ciudadId){
        reservaService.deleteCiudad(ciudadId);
        return new ResponseEntity<String>("Ciudad deleted succesfully!.", HttpStatus.OK);
    }

    @GetMapping("/pais/{id}")
    public List<Ciudad> getCiudadByPaisId(@PathVariable("id") Long paisId){
        return reservaService.getCiudadByPaisId(paisId);
    };

}
