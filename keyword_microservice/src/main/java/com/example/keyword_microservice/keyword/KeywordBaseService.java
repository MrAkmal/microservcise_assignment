package com.example.keyword_microservice.keyword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class KeywordBaseService {

    private final KeywordBaseRepository repository;

    @Autowired
    public KeywordBaseService(KeywordBaseRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Mono<KeywordBase> save(KeywordBaseDTO dto) {


        KeywordBase keywordBase = new KeywordBase(
                dto.getCountry(),
                dto.getCountry(),
                dto.getCountryWiseName()
        );

        return repository.findByCountry(dto.getCountry())
                .flatMap(Mono::just)
                .switchIfEmpty(repository.save(keywordBase));
    }

    @Transactional
    public Mono<KeywordBase> update(KeywordBaseDTO dto) {

        if (dto.getId() != 0) {
            return repository.findById(dto.getId())
                    .flatMap(keywordBase -> {
                        keywordBase.setGeneratedName(dto.getCountry());
                        keywordBase.setCountryWiseName(dto.getCountryWiseName());
                        keywordBase.setCountry(dto.getCountry());
                        if (!keywordBase.getCountry().equals(dto.getCountry())) {
                            return repository.findByCountry(dto.getCountry())
                                    .flatMap(Mono::just)
                                    .switchIfEmpty(repository.save(keywordBase));
                        } else {
                            return repository.save(keywordBase);
                        }
                    })
                    .switchIfEmpty(Mono.empty());
        }

        return Mono.empty();
    }

    @Transactional
    public Mono<Void> delete(int keywordBaseId) {

        return repository.existsById(keywordBaseId)
                .flatMap(keywordBase -> repository.deleteById(keywordBaseId))
                .switchIfEmpty(Mono.empty());
    }

    @Transactional
    public Mono<KeywordBase> get(int id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.empty());
    }

    @Transactional
    public Flux<KeywordBase> getAll() {
        return repository.findAll();
    }

}
