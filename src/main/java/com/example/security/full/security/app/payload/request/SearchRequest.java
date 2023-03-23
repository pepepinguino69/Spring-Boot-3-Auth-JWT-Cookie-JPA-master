package com.example.security.full.security.app.payload.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchRequest {

    private Long sucursalId;
    private Long categoriaId;
    private LocalDateTime comienzo_reserva;
    private LocalDateTime fin_reserva;




}
