package com.example.security.full.security.app.controller;


import com.example.security.full.security.app.model.Imagen;
import com.example.security.full.security.app.payload.request.ImagenRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/imagenes"))

public class ImagenController {

    private ReservaService reservaService;

    public ImagenController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Imagen> saveImagen(@RequestBody ImagenRequest imagenRequest) throws Exception {
        ;return new ResponseEntity<Imagen>(reservaService.saveImagen(imagenRequest), HttpStatus.CREATED);
    };

    @GetMapping("/")

    public List<Imagen> getAllImagenes(){
        return reservaService.getAllImagenes();
    }

    @GetMapping("{id}")

    public ResponseEntity<Imagen> getImagenById(@PathVariable("id") long imagenId){
        return new ResponseEntity<Imagen>(reservaService.getImagenById(imagenId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Imagen> updateImagen(@PathVariable("id") long imagenId,@RequestBody ImagenRequest imagenRequest){
        return new ResponseEntity<Imagen>(reservaService.updateImagen(imagenRequest, imagenId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteImagenBy(@PathVariable("id") long imagenId){
        reservaService.deleteImagen(imagenId);
        return new ResponseEntity<String>("Imagen deleted succesfully!.", HttpStatus.OK);
    }

}

