package com.example.security.full.security.app.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class ModeloRequest {

    private Long marca_id;
    private String nombre;
    private String descripcion;
    private Long categoria_id;
    private Double puntuacion;
    private ArrayList<Long> caracteristicas = new ArrayList<>();
}
