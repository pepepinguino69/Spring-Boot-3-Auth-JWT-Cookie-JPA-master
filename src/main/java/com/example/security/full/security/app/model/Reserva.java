package com.example.security.full.security.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;




@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "reserva")

public class Reserva {
        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;


    @JsonIgnore
        @ManyToOne
        @JoinColumn(
                name = "cliente_id", referencedColumnName = "id",nullable = false,

    foreignKey = @ForeignKey(
            name = "cliente_reserva_fk"
    ))
        private Cliente cliente;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
               name = "vehiculo_id",
               nullable = false,
               referencedColumnName = "id",
               foreignKey = @ForeignKey(
                       name = "vehiculo_reserva_fk"
               ))

        private Vehiculo vehiculo;


        @Column(name="comienzo_reserva",nullable = false)
        private LocalDateTime comienzo_reserva;
        @Column(name="fin_reserva",nullable = false)
        private LocalDateTime fin_reserva;




}



