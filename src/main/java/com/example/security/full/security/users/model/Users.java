package com.example.security.full.security.users.model;

import com.example.security.full.security.app.model.Ciudad;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;
import static java.lang.Boolean.FALSE;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo",
        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "email_unique", columnNames = "email"))
public class Users {
    @Id
    @GeneratedValue(
            strategy = AUTO)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "roles", nullable = false)
    private String Roles;
    private String confirmationToken;
    private LocalDate createdOn;
    private LocalDate confirmedOn;
    @ManyToOne
    private Ciudad ciudad;
    private Boolean enabled=FALSE;

    @ToString.Exclude
    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;


}
