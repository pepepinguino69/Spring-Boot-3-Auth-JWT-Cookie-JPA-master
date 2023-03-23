package com.example.security.full.security.app.model;

import com.example.security.full.security.users.model.Users;
import jakarta.persistence.*;
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
@DiscriminatorValue("1")

public class Cliente extends Users {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Reserva> reservasCli = new ArrayList<>();


    public void addReserva(Reserva reserva) {
        reservasCli.add(reserva);
        reserva.setCliente(this);
    }

    public void removeReserva(Reserva reserva) {
        reservasCli.add(reserva);
        reserva.setCliente(this);
    }
}


