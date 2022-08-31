package com.example.procurement_method_service.procurementMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class ProcurementMethodService {


    private final ProcurementMethodRepository repository;
    private final WebClient webClient;
    private final ProcurementMethodMapper mapper;

    @Value("${procurement_nature_base_url}")
    String procurementNatureBaseUrl;

    private final String keywordBaseUrl = "";


    @Autowired
    public ProcurementMethodService(ProcurementMethodRepository repository, WebClient webClient, ProcurementMethodMapper mapper) {
        this.repository = repository;
        this.webClient = webClient;
        this.mapper = mapper;
    }


    public Mono<ProcurementMethod> save(ProcurementMethodCreateDTO dto) {

        int procurementNatureId = dto.getProcurementNatureId();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION);

        Mono<ProcurementNatureDTO> procurementNature = getProcurementNature(procurementNatureId, authHeader);
        Mono<KeywordBaseDTO> keywordBase = getKeywordBase(dto.getKeywordBaseId(), authHeader);

        return repository.findAllByProcurementNatureIdAndKeywordBaseId(dto.getProcurementNatureId(), dto.getKeywordBaseId())
                .switchIfEmpty(procurementNature.flatMap(procurementNatureDTO -> keywordBase.flatMap(keywordBaseDTO ->
                        repository.save(mapper.fromCreateDTO(dto)))).switchIfEmpty(Mono.empty()))
                .flatMap(method -> Mono.empty());


    }


    public Mono<ProcurementMethodDTO> update(ProcurementMethodUpdateDTO dto) {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION);

        Mono<ProcurementNatureDTO> procurementNature = getProcurementNature(dto.getProcurementNatureId(), authHeader);
        Mono<KeywordBaseDTO> keywordBase = getKeywordBase(dto.getKeywordBaseId(), authHeader);

        Mono<Tuple2<ProcurementNatureDTO, KeywordBaseDTO>> zip = Mono.zip(procurementNature, keywordBase);

        return repository.findById(dto.getId())
                .flatMap(method ->

                        zip.flatMap(objects ->
                                repository
                                        .findAllByProcurementNatureIdAndKeywordBaseId(objects.getT1().getId(), objects.getT2().getId())
                                        .map(method1 -> mapper.toDTO(method1.getId(), objects.getT2().getWiseName(), objects.getT1().getName()))
                                        .switchIfEmpty(
                                                repository.save(mapper.fromUpdateDTO(dto)).map(method1 -> mapper.toDTO(method1.getId(), objects.getT2().getWiseName(), objects.getT1().getName()))
                                        )
                        ))

                .switchIfEmpty(Mono.empty());


    }


    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }


    public Mono<ProcurementMethodDTO> get(Integer id, String authHeader) {

        return repository.findById(id)
                .flatMap(method -> {
                    Mono<ProcurementNatureDTO> procurementNature = getProcurementNature(method.getProcurementNatureId(), authHeader);
                    Mono<KeywordBaseDTO> keywordBase = getKeywordBase(method.getKeywordBaseId(), authHeader);
                    Mono<Tuple2<ProcurementNatureDTO, KeywordBaseDTO>> zip = Mono.zip(procurementNature, keywordBase);

                    return zip.map(objects -> mapper.toDTO(method.getId(),
                            objects.getT2().getWiseName(),
                            objects.getT1().getName()));
                })
                .switchIfEmpty(Mono.empty());

    }

    public Flux<ProcurementMethod> getAll() {

        Flux<ProcurementMethod> list = repository.findAll();

        System.out.println("list = " + list);

        return list;

    }


    public Flux<ProcurementMethodDTO> getAllSort(String fieldName) {


        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION);

        Flux<ProcurementMethod> list = repository.findAll(Sort.by(Sort.Direction.ASC, fieldName));

        return list.flatMap(method -> get(method.getId(), authHeader)).switchIfEmpty(Mono.empty());


    }


    public Mono<ProcurementNatureDTO> getProcurementNature(int id, String authHeader) {


        return webClient.get()
                .uri(procurementNatureBaseUrl + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .bodyToMono(ProcurementNatureDTO.class);
    }

    public Mono<KeywordBaseDTO> getKeywordBase(int id, String authHeader) {


        return webClient.get()
                .uri(keywordBaseUrl + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .bodyToMono(KeywordBaseDTO.class);
    }

    public Mono<Void> deleteProcurementMethodByProcurementNatureId(Integer procurementNatureId) {

        return repository.updateByProcurementNatureId(procurementNatureId);
//        return repository.deleteByProcurementNatureId(procurementNatureId);
    }

}
