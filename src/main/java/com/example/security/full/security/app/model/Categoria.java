package com.example.security.full.security.app.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categoria")


public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    @Size(max = 40)
    private String nombre;


    @Size(max = 100)
    private String descripcion;


    @Size(max = 100)
    private String urlImagen;

    @OneToMany(
            mappedBy = "categoria",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Modelo> modelosCat = new ArrayList<>();


    public void addModelo(Modelo modelo) {
        modelosCat.add(modelo);
        modelo.setCategoria(this);
    }





}
