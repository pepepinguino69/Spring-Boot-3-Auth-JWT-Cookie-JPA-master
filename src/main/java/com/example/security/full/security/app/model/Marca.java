package com.example.security.full.security.app.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

;



@Getter
@Setter
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(
            mappedBy = "marca",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Modelo> modeloMar = new ArrayList<>();

    public void addModelo(Modelo modelo) {
        modeloMar.add(modelo);
        modelo.setMarca(this);
    }



}
