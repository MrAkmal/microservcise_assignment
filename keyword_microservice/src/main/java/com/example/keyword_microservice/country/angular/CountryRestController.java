package com.example.keyword_microservice.country.angular;

import com.example.keyword_microservice.country.CountryBase;
import com.example.keyword_microservice.country.CountryBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
@CrossOrigin("*")
public class CountryRestController {


    private final CountryBaseService service;

    @Autowired
    public CountryRestController(CountryBaseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Flux<CountryBase>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
