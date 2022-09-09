package com.example.keyword_microservice.keyword;

import com.example.keyword_microservice.country.CountryBase;
import com.example.keyword_microservice.country.CountryBaseService;
import com.example.keyword_microservice.egpcountry.EgpCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class KeywordBaseService {

    private final KeywordBaseRepository repository;
    private final CountryBaseService countryBaseService;
    private final EgpCountryService egpCountryService;

    private final KeywordBaseMapper mapper;


    @Autowired
    public KeywordBaseService(KeywordBaseRepository repository, CountryBaseService countryBaseService, EgpCountryService egpCountryService, KeywordBaseMapper mapper) {
        this.repository = repository;
        this.countryBaseService = countryBaseService;
        this.egpCountryService = egpCountryService;
        this.mapper = mapper;
    }


    @Transactional
    public Mono<KeywordBase> save(KeywordBaseCreateDTO dto) {


        return countryBaseService.get(dto.getCountryId())
                .flatMap(countryBase ->
                        repository.checkKeywordNotExist(dto)
                                .switchIfEmpty(
                                        repository.save(mapper.fromDTO(dto)
                                        )))
                .switchIfEmpty(Mono.empty());


    }

    @Transactional
    public Mono<KeywordBaseDTO> update(KeywordBaseUpdateDTO dto) {

        Mono<CountryBase> countryBaseMono = countryBaseService.get(dto.getCountryId());

        return countryBaseMono.flatMap(countryBase -> repository.findById(dto.getId())
                .flatMap(keywordBase -> {

                    Mono<KeywordBase> keywordBaseMono = repository.checkKeywordNotExist(
                                    new KeywordBaseCreateDTO(dto.getGenericName(),
                                            dto.getCountryId(), dto.getWiseName()))
                            .switchIfEmpty(repository.save(mapper.fromUpdateDto(keywordBase, dto))
                            );

                    return keywordBaseMono.map(keywordBase1 -> mapper.toDTO(keywordBase1, ""));


                })
                .switchIfEmpty(Mono.empty())).switchIfEmpty(Mono.empty());

    }

    @Transactional
    public Mono<Void> delete(int keywordBaseId) {

        return repository.existsById(keywordBaseId)
                .flatMap(keywordBase -> repository.deleteById(keywordBaseId))
                .switchIfEmpty(Mono.empty());
    }

    @Transactional
    public Mono<KeywordBaseDTO> get(int id) {
        return repository.findById(id)
                .flatMap(keywordBase -> countryBaseService.get(keywordBase.getCountryId())
                        .map(countryBase -> mapper.toDTO(keywordBase, countryBase.getName())))
                .switchIfEmpty(Mono.empty());
    }

    @Transactional
    public Flux<KeywordBaseDTO> getAll() {

        return repository.findAll()
                .flatMap(keywordBase -> countryBaseService.get(keywordBase.getCountryId())
                        .map(countryBase -> mapper.toDTO(keywordBase, countryBase.getName())))
                .switchIfEmpty(Flux.empty());
    }

    public Mono<KeywordBase> getKeywordBase(Integer keywordBaseId) {
        return repository.findById(keywordBaseId)
                .switchIfEmpty(Mono.empty());
    }

    public Flux<KeywordBase> getWiseName() {

        Flux<Integer> defaultCountryId = egpCountryService.getDefaultCountry();
        System.out.println("Start");
        Flux<KeywordBase> keywordBaseFlux = defaultCountryId.flatMap(repository::findAllByCountryId);
        keywordBaseFlux.subscribe(s->System.out.println(s));
        return keywordBaseFlux;
    }

    public Mono<KeywordBaseDTO> getKeywordBaseByDefaultCountry(Integer keywordBaseId, Integer defaultCountryId) {
        return repository.findByCountryIdAndId(defaultCountryId,keywordBaseId)
                .map(mapper::toDTOMenu)
                .switchIfEmpty(Mono.empty());
    }
}
