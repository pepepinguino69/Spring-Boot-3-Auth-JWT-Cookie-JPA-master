package com.example.security.full.security.auth.controller.registration.token;


import com.example.security.full.security.users.model.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {



    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY

    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private Users users;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             Users users) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.users = users;
    }
}
