package com.example.security.full.security.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="imagen")





public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    @Size(max = 40)
    private String nombre;


    @Size(max = 100)
    private String urlImagen;

    @ManyToOne
    @JoinColumn(
            name = "modelo_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "modelo_fk"
            ))
    @JsonIgnore
    Modelo modelo;


}
