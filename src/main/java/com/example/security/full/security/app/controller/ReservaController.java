package com.example.security.full.security.app.controller;

import com.example.security.full.security.app.model.Reserva;
import com.example.security.full.security.app.payload.request.ReservaRequest;
import com.example.security.full.security.app.services.ReservaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(("/api/reservas"))
public class ReservaController {

    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @PostMapping("/")
    public ResponseEntity<Reserva> saveReserva(@RequestBody ReservaRequest reservaRequest) throws Exception {
        ;return new ResponseEntity<Reserva>(reservaService.saveReserva(reservaRequest), HttpStatus.CREATED);
                                               //long odontologo_id, long paciente_id, LocalDateTime comienzo_reserva, LocalDateTime fin_reserva){
       // return new ResponseEntity<Reserva>(reservaService.saveReserva(odontologo_id,paciente_id,comienzo_reserva,fin_reserva), HttpStatus.CREATED);
    };
    @GetMapping("/")
    public List<Reserva> getAllReservas(){
        return reservaService.getAllReservas();
    }

    @GetMapping("{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable("id") long reservaId){
        return new ResponseEntity<Reserva>(reservaService.getReservaById(reservaId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable("id") long reservaId, @RequestBody ReservaRequest reservaRequest){
        return new ResponseEntity<Reserva>(reservaService.updateReserva(reservaRequest, reservaId), HttpStatus.OK);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteReservaBy(@PathVariable("id") long reservaId){
        reservaService.deleteReserva(reservaId);
        return new ResponseEntity<String>("Reserva deleted succesfully!.", HttpStatus.OK);
    }
    @GetMapping("/sucursal/{id}")
    public List<Reserva> getReservaBySucursalId(@PathVariable("id") long sucursalId) {
        return reservaService.getReservaXSucursal(sucursalId);

    }

}
