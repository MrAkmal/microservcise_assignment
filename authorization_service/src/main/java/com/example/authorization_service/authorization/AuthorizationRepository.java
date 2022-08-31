package com.example.authorization_service.authorization;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Token,Long> {



    Optional<Token> findByAccessExpiresAtAfterAndAccessToken(LocalDateTime accessExpiresAt, String accessToken);


}
