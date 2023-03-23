package com.example.security.full.security.app.controller;


import com.example.security.full.security.app.model.Pais;
import com.example.security.full.security.app.payload.request.PaisRequest;
import com.example.security.full.security.app.services.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/paises"))

public class PaisController {

    private ReservaService reservaService;

    public PaisController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Pais> savePais(@RequestBody PaisRequest paisRequest){
        ;return new ResponseEntity<Pais>(reservaService.savePais(paisRequest), HttpStatus.CREATED);
    };
    @GetMapping("/")

    public List<Pais> getAllPaises(){
        return reservaService.getAllPaises();
    }

    @GetMapping("{id}")

    public ResponseEntity<Pais> getPaisById(@PathVariable("id") long paisId){
        return new ResponseEntity<Pais>(reservaService.getPaisById(paisId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Pais> updatePaises(@PathVariable("id") long paisId, @RequestBody Pais pais){
        return new ResponseEntity<Pais>(reservaService.updatePais(pais, paisId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deletePaisesBy(@PathVariable("id") long paisId){
        reservaService.deletePais(paisId);
        return new ResponseEntity<String>("Pais deleted succesfully!.", HttpStatus.OK);
    }

}
