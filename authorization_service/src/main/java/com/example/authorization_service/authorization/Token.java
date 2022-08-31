package com.example.authorization_service.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token_store")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String accessToken;

    private LocalDateTime accessExpiresAt;

    private String refreshToken;

    private LocalDateTime refreshExpiresAt;


    public Token(String accessToken, LocalDateTime accessExpiresAt, String refreshToken, LocalDateTime refreshExpiresAt) {
        this.accessToken = accessToken;
        this.accessExpiresAt = accessExpiresAt;
        this.refreshToken = refreshToken;
        this.refreshExpiresAt = refreshExpiresAt;
    }
}
