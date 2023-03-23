package com.example.security.full.security.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehiculo")
public class Vehiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 20)
    private String patente;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "pais_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "pais_vehiculo_fk"
            ))
    private Pais pais;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "modelo_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "modelo_vehiculo_fk"
            ))

    private Modelo modelo;


    @OneToMany(
            mappedBy = "vehiculo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Reserva> reservasVeh = new ArrayList<>();
    public void addReserva(Reserva reserva) {
        reservasVeh.add(reserva);
        reserva.setVehiculo(this);
    }

    private Long kilometraje;
    private Boolean disponible;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "sucursal_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "sucursal_fk"
            ))
    Sucursal sucursal;



}
