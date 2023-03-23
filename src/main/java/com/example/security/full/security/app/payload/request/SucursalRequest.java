package com.example.security.full.security.app.payload.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SucursalRequest {


    private Long ciudadId;

    private String nombre;

    private String direccion;

    private String latitud;

    private String longitud;


}

