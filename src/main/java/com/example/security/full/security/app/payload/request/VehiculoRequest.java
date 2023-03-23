package com.example.security.full.security.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoRequest {


    private Long modeloId;
    private Long paisId;
    private String  patente;
    private Long kilometraje;
    private Boolean disponible;
    }

