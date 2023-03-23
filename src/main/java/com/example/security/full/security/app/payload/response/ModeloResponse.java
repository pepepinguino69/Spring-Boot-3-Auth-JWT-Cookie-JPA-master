package com.example.security.full.security.app.payload.response;

import com.example.security.full.security.app.model.Caracteristica;
import com.example.security.full.security.app.model.Imagen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class ModeloResponse {
        private Long modelo_id;
        private Long marca_id;
        private String marca;
        private String nombre;
        private String descripcion;
        private Long categoria_id;
        private Double puntuacion;
        private String categoria;
        private Set<Caracteristica> caracteristicas = new HashSet<>();
        private List<Imagen> imagenes = new ArrayList<>();
    }


