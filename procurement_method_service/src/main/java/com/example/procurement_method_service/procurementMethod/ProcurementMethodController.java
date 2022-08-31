package com.example.procurement_method_service.procurementMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/v1/procurement_method")
public class ProcurementMethodController {


    private final ProcurementMethodService service;

    @Autowired
    public ProcurementMethodController(ProcurementMethodService service) {
        this.service = service;
    }


    @PostMapping
    public Mono<ProcurementMethod> save(@Valid @RequestBody ProcurementMethodCreateDTO dto) {
        System.out.println("method.getName() = " + dto);

        return service.save(dto);
    }


    @PutMapping
    public Mono<ProcurementMethodDTO> update(@Valid @RequestBody ProcurementMethodUpdateDTO dto) {
        System.out.println("dto = " + dto);
        return service.update(dto);

    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {

        return service.delete(id);

    }


    @GetMapping("/{id}")
    public Mono<ProcurementMethodDTO> get(@PathVariable Integer id) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION);
        System.out.println("id = " + id);
        return service.get(id,authHeader);

    }

    @GetMapping("/list")
    public Flux<ProcurementMethod> getAll() {

        return service.getAll();
    }


    @GetMapping
    public Flux<ProcurementMethodDTO> getAllSort(@RequestParam(required = false, defaultValue = "id") String fieldName) {
        return service.getAllSort(fieldName);
    }

    @DeleteMapping("/delete_by_procurement_nature_id/{procurementNatureId}")
    public Mono<Void> deleteProcurementMethodByProcurementNatureId(@PathVariable Integer procurementNatureId) {
        System.out.println("procurementNatureId = " + procurementNatureId);
        return service.deleteProcurementMethodByProcurementNatureId(procurementNatureId);
    }

}
