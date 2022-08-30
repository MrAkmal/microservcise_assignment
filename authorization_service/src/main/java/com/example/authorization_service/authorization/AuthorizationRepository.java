package com.example.authorization_service.authorization;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Token,Long> {
}
