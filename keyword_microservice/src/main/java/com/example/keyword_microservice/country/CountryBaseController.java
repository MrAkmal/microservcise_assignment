package com.example.keyword_microservice.country;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/country_base_server")
public class CountryBaseController {
    private final CountryBaseService service;

    @Autowired
    public CountryBaseController(CountryBaseService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<CountryBase> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<CountryBase> get(@PathVariable Integer id) {
        return service.get(id);
    }
}
