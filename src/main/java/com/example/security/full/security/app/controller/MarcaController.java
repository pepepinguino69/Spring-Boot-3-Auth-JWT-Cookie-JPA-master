package com.example.security.full.security.app.controller;

import com.example.security.full.security.app.model.Marca;
import com.example.security.full.security.app.payload.request.MarcaRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/marcas"))

public class MarcaController {

    private ReservaService reservaService;

    public MarcaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Marca> saveMarca(@RequestBody MarcaRequest marcaRequest) throws Exception {
        ;return new ResponseEntity<Marca>(reservaService.saveMarca(marcaRequest), HttpStatus.CREATED);
    };
    @GetMapping("/")

    public List<Marca> getAllMarcas(){
        return reservaService.getAllMarcas();
    }

    @GetMapping("{id}")

    public ResponseEntity<Marca> getMarcaById(@PathVariable("id") long marcaId){
        return new ResponseEntity<Marca>(reservaService.getMarcaById(marcaId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Marca> updateMarca(@PathVariable("id") long marcaId, @RequestBody MarcaRequest marcaRequest){
        return new ResponseEntity<Marca>(reservaService.updateMarca(marcaRequest, marcaId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteMarcaesBy(@PathVariable("id") long marcaId){
        reservaService.deleteMarca(marcaId);
        return new ResponseEntity<String>("Marca deleted succesfully!.", HttpStatus.OK);
    }

}
