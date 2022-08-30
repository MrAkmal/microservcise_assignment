package com.example.procurement_nature_service.procurementNature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/v1/procurement_nature")
public class ProcurementNatureController {

    private final ProcurementNatureService service;

    @Autowired
    public ProcurementNatureController(ProcurementNatureService service) {
        this.service = service;
    }


    @PostMapping
    public Mono<ProcurementNature> save(@RequestBody ProcurementNature procurementMethod) {

        return service.save(procurementMethod);
    }


    @PutMapping
    public Mono<ProcurementNature> update(@RequestBody ProcurementNature method) {

        return service.update(method);

    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {

        return service.delete(id);

    }


    @GetMapping("/{id}")
    public Mono<ProcurementNature> get(@PathVariable Integer id) {

        System.out.println("id = " + id);
        return service.get(id);

    }

    @GetMapping("/list")
    public Flux<ProcurementNature> getAll() {

        return service.getAll();
    }

    @GetMapping
    public Flux<ProcurementNature> getAllSort(@RequestParam(required = false, defaultValue = "id") String fieldName) {

        return service.getAll(fieldName);
    }


}
