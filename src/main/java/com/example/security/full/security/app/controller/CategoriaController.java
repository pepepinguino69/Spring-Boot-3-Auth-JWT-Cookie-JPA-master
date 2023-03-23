package com.example.security.full.security.app.controller;


import com.example.security.full.security.app.model.Categoria;

import com.example.security.full.security.app.payload.request.CategoriaRequest;
import com.example.security.full.security.app.services.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/categorias"))

public class CategoriaController {


    private ReservaService reservaService;

    public CategoriaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Categoria> saveCategoria(@RequestBody CategoriaRequest categoriaRequest) throws Exception {
        ;return new ResponseEntity<Categoria>(reservaService.saveCategoria(categoriaRequest), HttpStatus.CREATED);
    };

    @GetMapping("/")

    public List<Categoria> getAllCategorias(){
        return reservaService.getAllCategorias();
    }

    @GetMapping("{id}")

    public ResponseEntity<Categoria> getCategoriaById(@PathVariable("id") long categoriaId){
        return new ResponseEntity<Categoria>(reservaService.getCategoriaById(categoriaId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Categoria> updateCategoria(@PathVariable("id") long categoriaId,@RequestBody Categoria categoria){
        return new ResponseEntity<Categoria>(reservaService.updateCategoria(categoria, categoriaId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<String> deleteCategoriaBy(@PathVariable("id") long categoriaId){
        reservaService.deleteCategoria(categoriaId);
        return new ResponseEntity<String>("Categoria deleted succesfully!.", HttpStatus.OK);
    }

}
