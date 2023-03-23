package com.example.security.full.security.app.controller;

import com.example.security.full.security.app.model.Modelo;
import com.example.security.full.security.app.payload.request.ModeloRequest;
import com.example.security.full.security.app.payload.response.ModeloResponse;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/modelos"))

public class ModeloController {

    private ReservaService reservaService;

    public ModeloController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Modelo> saveModelo(@RequestBody ModeloRequest modeloRequest) throws Exception {
        ;return new ResponseEntity<Modelo>(reservaService.saveModelo(modeloRequest), HttpStatus.CREATED);
    };
    @GetMapping("/")

    public List<Modelo> getAllModelos(){
        return reservaService.getAllModelos();
    }

    @GetMapping("/detailed/{nombre}")

    public List<Modelo> getModeloByNombre(@PathVariable("nombre") String nombre){
        return reservaService.getModeloByNombre(nombre);
    };


    @GetMapping("{id}")

    public ResponseEntity<Modelo> getModeloById(@PathVariable("id") long modeloId){
        return new ResponseEntity<Modelo>(reservaService.getModeloById(modeloId), HttpStatus.OK);
    }



    @GetMapping("/categoria/{id}")
    public List<Modelo> getModeloByCategoriaId(@PathVariable("id") Long id){
        return reservaService.getModeloByCategoriaId(id);
    };

    @GetMapping("/random")
    public List<Modelo> getRandomModelos(){
        return reservaService.getRandomModelos();

    }

    @GetMapping("/cards/")
    public List<ModeloResponse> getDTOModelos(){
        return reservaService.getDTOModelos();

    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Modelo> updateModelo(@PathVariable("id") long modeloId, @RequestBody ModeloRequest modeloRequest){
        return new ResponseEntity<Modelo>(reservaService.updateModelo(modeloRequest, modeloId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<String> deleteModeloBy(@PathVariable("id") long modeloId){
        reservaService.deleteModelo(modeloId);
        return new ResponseEntity<String>("Modelo deleted succesfully!.", HttpStatus.OK);
    }

}
