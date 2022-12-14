package com.example.keyword_microservice.egpcountry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/key_word_base/egp_country_server")
public class EgpCountryController {

    private final EgpCountryService service;

    @Autowired
    public EgpCountryController(EgpCountryService service) {
        this.service = service;
    }


    @GetMapping
    public Flux<EgpCountryDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<EgpCountryDTO> get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PostMapping
    public Mono<EgpCountryDTO> save(@Valid @RequestBody EgpCountryCreateDTO dto) {
        return service.save(dto);
    }

    @PutMapping
    public Mono<EgpCountryDTO> save(@Valid @RequestBody EgpCountryUpdateDTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id);
    }



    @GetMapping("/default_country_id")
    public Mono<Integer> getDefaultCountryId(){
        return service.getDefaultCountryId();
    }


}
