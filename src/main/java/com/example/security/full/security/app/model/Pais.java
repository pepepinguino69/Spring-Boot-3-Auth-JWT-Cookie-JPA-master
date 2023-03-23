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

public class Pais {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "nombre")
    private String nombre;

    @JsonIgnore
    @OneToMany(
            mappedBy = "pais",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Vehiculo> vehiculosPai = new ArrayList<>();

    public void addVehiculo(Vehiculo vehiculo) {
        vehiculosPai.add(vehiculo);
        vehiculo.setPais(this);
    }

    @JsonIgnore
    @OneToMany(
            mappedBy = "pais",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Ciudad> ciudadPai = new ArrayList<>();

    public void addCiudad(Ciudad ciudad) {
        ciudadPai.add(ciudad);
        ciudad.setPais(this);
    }


}
