package com.example.procurement_method_service.procurementMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/procurement_method")
public class ProcurementMethodController {


    private final ProcurementMethodService service;

    @Autowired
    public ProcurementMethodController(ProcurementMethodService service) {
        this.service = service;
    }


    @PostMapping
    public Mono<ProcurementMethod> save(@Valid @RequestBody ProcurementMethodDTO procurementMethod) {
        System.out.println("method.getName() = " + procurementMethod.getName());

        return service.save(procurementMethod);
    }


    @PutMapping
    public Mono<ProcurementMethod> update(@Valid @RequestBody ProcurementMethodDTO method) {
        System.out.println("method.getName() = " + method.getName());
        return service.update(method);

    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {

        return service.delete(id);

    }


    @GetMapping("/{id}")
    public Mono<ProcurementMethod> get(@PathVariable Integer id) {

        System.out.println("id = " + id);
        return service.get(id);

    }

    @GetMapping("/list")
    public Flux<ProcurementMethod> getAll() {

        return service.getAll();
    }


    @GetMapping
    public Flux<ProcurementMethodResponse> getAllSort(@RequestParam(required = false, defaultValue = "id") String fieldName) {

        return service.getAllSort(fieldName);
    }

    @DeleteMapping("/delete_by_procurement_nature_id/{procurementNatureId}")
    public Mono<Void> deleteProcurementMethodByProcurementNatureId(@PathVariable Integer procurementNatureId) {
        System.out.println("procurementNatureId = " + procurementNatureId);
        return service.deleteProcurementMethodByProcurementNatureId(procurementNatureId);
    }

}
