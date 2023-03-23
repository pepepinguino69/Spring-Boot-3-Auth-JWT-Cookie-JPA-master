package com.example.security.full.security.app.controller;

import com.example.security.full.security.app.model.Sucursal;
import com.example.security.full.security.app.payload.request.SucursalRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/sucursales"))

public class SucursalController {

    private ReservaService reservaService;

    public SucursalController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Sucursal> saveSucursal(@RequestBody SucursalRequest sucursalRequest) throws Exception {
        ;return new ResponseEntity<Sucursal>(reservaService.saveSucursal(sucursalRequest), HttpStatus.CREATED);
    };
    @GetMapping("/")

    public List<Sucursal> getAllSucursales(){
        return reservaService.getAllSucursales();
    }

    @GetMapping("{id}")

    public ResponseEntity<Sucursal> getSucursalById(@PathVariable("id") long sucursalId){
        return new ResponseEntity<Sucursal>(reservaService.getSucursalById(sucursalId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Sucursal> updateSucursal(@PathVariable("id") long sucursalId, @RequestBody SucursalRequest sucursalRequest){
        return new ResponseEntity<Sucursal>(reservaService.updateSucursal(sucursalRequest, sucursalId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<String> deleteSucursalBy(@PathVariable("id") long sucursalId){
        reservaService.deleteSucursal(sucursalId);
        return new ResponseEntity<String>("Sucursal deleted succesfully!.", HttpStatus.OK);
    }

    @GetMapping("/ciudad/{id}")
    public List<Sucursal> getSucursalByCiudadId(@PathVariable("id") Long ciudadId){
        return reservaService.getSucursalByCiudadId(ciudadId);
    };

}
