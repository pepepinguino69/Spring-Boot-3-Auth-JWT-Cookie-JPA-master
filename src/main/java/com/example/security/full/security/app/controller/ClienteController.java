package com.example.security.full.security.app.controller;

import com.example.security.full.security.app.model.Cliente;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/clientes"))
public class ClienteController {

    private ReservaService reservaService;

    public ClienteController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente){
        return new ResponseEntity<Cliente>(reservaService.saveCliente(cliente), HttpStatus.CREATED);
    };

    @GetMapping("/")

    public List<Cliente> getAllClientes(){
        return reservaService.getAllClientes();
    }

    @GetMapping("{id}")

    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") long clienteId){
        return new ResponseEntity<Cliente>(reservaService.getClienteById(clienteId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") long clienteId, @RequestBody Cliente cliente){
        return new ResponseEntity<Cliente>(reservaService.updateCliente(cliente, clienteId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteClienteBy(@PathVariable("id") long clienteId){
        reservaService.deleteCliente(clienteId);
        return new ResponseEntity<String>("Cliente deleted succesfully!.", HttpStatus.OK);
    }

}
