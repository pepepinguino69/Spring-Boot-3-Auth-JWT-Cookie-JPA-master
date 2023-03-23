package com.example.security.full.security.app.controller;


import com.example.security.full.security.app.model.Vehiculo;
import com.example.security.full.security.app.payload.request.VehiculoRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/vehiculos"))

public class VehiculoController {

    private ReservaService reservaService;

    public VehiculoController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Vehiculo> saveVehiculo(@RequestBody VehiculoRequest vehiculoRequest){
        ;return new ResponseEntity<Vehiculo>(reservaService.saveVehiculo(vehiculoRequest), HttpStatus.CREATED);
    };
    @GetMapping("/")

    public List<Vehiculo> getAllVehiculos(){
        return reservaService.getAllVehiculos();
    }

    @GetMapping("{id}")

    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable("id") Long vehiculoId){
        return new ResponseEntity<Vehiculo>(reservaService.getVehiculoById(vehiculoId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable("id") Long vehiculoId, @RequestBody VehiculoRequest vehiculoRequest){
        return new ResponseEntity<Vehiculo>(reservaService.updateVehiculo(vehiculoRequest, vehiculoId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteVehiculoBy(@PathVariable("id") Long vehiculoId){
        reservaService.deleteVehiculo(vehiculoId);
        return new ResponseEntity<String>("Vehiculo deleted succesfully!.", HttpStatus.OK);
    }

    @GetMapping("/sucursal/{id}")
    public List<Vehiculo> getVehiculoBySucursalId(@PathVariable("id") Long sucursalId){
        return reservaService.getVehiculoBySucursalId(sucursalId);
    };

    @GetMapping("/categoria/{id}")
    public List<Vehiculo> getVehiculosByModeloCategoriaId(@PathVariable("id") Long categoriaId){
        return reservaService.getVehiculosByModeloCategoriaId(categoriaId);
    };

}
