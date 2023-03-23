package com.example.security.full.security.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "sucursal")
public class Sucursal {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "latitud")
    private String latitud;
    @Column(name = "longitud")
    private String longitud;


    @ManyToOne
    @JoinColumn(
            name = "ciudad_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ciudad_fk"
            ))
    @JsonIgnore
    private Ciudad ciudad;

    @JsonIgnore
    @OneToMany(
            mappedBy = "sucursal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Vehiculo> vehiculosSuc = new ArrayList<>();

    public void addVehiculo(Vehiculo vehiculo) {
        vehiculosSuc.add(vehiculo);
        vehiculo.setSucursal(this);
    }




}

