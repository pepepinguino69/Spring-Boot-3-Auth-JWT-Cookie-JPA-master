package com.example.security.full.security.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;
    

    @Column(name="puntuacion")
    private Double puntuacion;

    @OneToMany(
            mappedBy = "modelo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Vehiculo> modelosVeh = new ArrayList<>();

    @OneToMany(
            mappedBy = "modelo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Imagen> modelosIma = new ArrayList<>();


    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "categoria_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "categoria_fk"
            ))
    Categoria categoria;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "marca_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "marca_fk"
            ))
    Marca marca;

    public void addImagen(Imagen imagen) {
        modelosIma.add(imagen);
        imagen.setModelo(this);
    }
    public void addVehiculo(Vehiculo vehiculo) {
        modelosVeh.add(vehiculo);
        vehiculo.setModelo(this);
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "caracteristicas",
            joinColumns = @JoinColumn(name = "caracteristica_id"),
            inverseJoinColumns = @JoinColumn(name = "modelo_id"))
    private Set<Caracteristica> caracteristicas = new HashSet<>();







}
