package com.example.security.full.security.app.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservaRequest {

   // private ArrayList<Long> conductores= new ArrayList<Long>();
    private Long cliente_id;
    private Long vehiculo_id;
    private LocalDateTime comienzo_reserva;
    private LocalDateTime fin_reserva;



}
