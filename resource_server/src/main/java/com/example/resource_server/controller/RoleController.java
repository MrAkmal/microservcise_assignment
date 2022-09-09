package com.example.resource_server.controller;

import com.example.resource_server.entity.UserRole;
import com.example.resource_server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/role")
public class RoleController {


    private final RoleService service;


    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }


    @GetMapping()
    public ResponseEntity<List<UserRole>> getAll(){
        return service.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserRole> get(@PathVariable("id") Integer roleId){
        return service.getById(roleId);
    }


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserRole userRole){
        return service.create(userRole.getRoleName());
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserRole userRole){
        return service.update(userRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer roleId){
        return service.delete(roleId);
    }

    @GetMapping("/name/{roleName}")
    public ResponseEntity<UserRole> get(@PathVariable("roleName") String roleName){
        return service.getByRoleName(roleName);
    }


}
