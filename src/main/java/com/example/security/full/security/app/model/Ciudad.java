package com.example.security.full.security.app.model;

import com.example.security.full.security.users.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ciudad")
public class Ciudad {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String nombre;

    @JsonIgnore
    @OneToMany(
            mappedBy = "ciudad",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Sucursal> sucursalesCiu = new ArrayList<>();
    public void addSucursal(Sucursal sucursal) {
        sucursalesCiu.add(sucursal);
        sucursal.setCiudad(this);
    }

    @JsonIgnore
    @OneToMany(
            mappedBy = "ciudad",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Users> usersCiu = new ArrayList<>();
    public void addUser(Users user) {
        usersCiu.add(user);
        user.setCiudad(this);
    }



    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "pais_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "pais_ciudad_fk"
            ))
    private Pais pais;


}
