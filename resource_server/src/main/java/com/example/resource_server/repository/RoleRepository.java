package com.example.resource_server.repository;


import com.example.resource_server.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {



    Optional<UserRole> findByRoleName(String roleName);

}
