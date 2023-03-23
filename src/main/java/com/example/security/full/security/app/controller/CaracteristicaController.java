package com.example.security.full.security.app.controller;


import com.example.security.full.security.app.model.Caracteristica;
import com.example.security.full.security.app.payload.request.CaracteristicaRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/caracteristicas"))

public class CaracteristicaController {

    private ReservaService reservaService;

    public CaracteristicaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Caracteristica> saveCaracteristica(@RequestBody CaracteristicaRequest caracteristicaRequest) throws Exception {
        ;return new ResponseEntity<Caracteristica>(reservaService.saveCaracteristica(caracteristicaRequest), HttpStatus.CREATED);
    };

    @GetMapping("/")

    public List<Caracteristica> getAllCaracteristicas(){
        return reservaService.getAllCaracteristicas();
    }

    @GetMapping("{id}")

    public ResponseEntity<Caracteristica> getCaracteristicaById(@PathVariable("id") long caracteristicaId){
        return new ResponseEntity<Caracteristica>(reservaService.getCaracteristicaById(caracteristicaId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Caracteristica> updateCaracteristica(@PathVariable("id") long caracteristicaId,@RequestBody CaracteristicaRequest caracteristicaRequest){
        return new ResponseEntity<Caracteristica>(reservaService.updateCaracteristica(caracteristicaRequest, caracteristicaId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<String> deleteCaracteristicaBy(@PathVariable("id") long caracteristicaId){
        reservaService.deleteCaracteristica(caracteristicaId);
        return new ResponseEntity<String>("Caracteristica deleted succesfully!.", HttpStatus.OK);
    }

}

