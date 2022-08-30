package com.example.authorization_service.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authorization_server")
public class AuthorizationController {

    private final AuthorizationService service;


    @Autowired
    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }


    @PostMapping("/generate-token")
    public ResponseEntity<AuthorizationDTO> generateToken(@RequestBody RequestDTO dto) {
        return ResponseEntity.ok(service.generateToken(dto));
    }


}
