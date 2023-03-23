package com.example.security.full.security.app.payload.request;



import com.example.security.full.security.app.model.Caracteristica;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchAllRequest {

    private String id;
    private Long sucursalId;
    private Long categoriaId;
    private LocalDateTime comienzo_reserva;
    private LocalDateTime fin_reserva;
    private List<Caracteristica> caracteristicas = new ArrayList<>();




}
