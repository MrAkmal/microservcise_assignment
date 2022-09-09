package com.example.resource_server.service;

import com.example.resource_server.entity.UserRole;
import com.example.resource_server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {


    private final RoleRepository repository;


    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<UserRole>> getAll() {

        List<UserRole> userRoles = repository.findAll();
        return userRoles.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    public ResponseEntity<UserRole> getById(Integer roleId) {
        Optional<UserRole> optional = repository.findById(roleId);

        if (optional.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    public ResponseEntity<Void> create(String roleName) {

        Optional<UserRole> isAvailable = repository.findByRoleName(roleName);

        if (isAvailable.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        repository.save(new UserRole(roleName));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> update(UserRole userRole) {

        Optional<UserRole> optionalById = repository.findById(userRole.getId());

        if (optionalById.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<UserRole> optionalByRoleName = repository.findByRoleName(userRole.getRoleName());

        if (optionalByRoleName.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        repository.save(userRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Void> delete(Integer roleId) {

        Optional<UserRole> optionalUserRole = repository.findById(roleId);

        if (optionalUserRole.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        repository.deleteById(roleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<UserRole> getByRoleName(String roleName) {

        Optional<UserRole> optional = repository.findByRoleName(roleName);
        if (optional.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }
}
