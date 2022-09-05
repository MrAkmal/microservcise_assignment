package com.example.keyword_microservice.egpcountry;

import com.example.keyword_microservice.country.CountryBase;
import com.example.keyword_microservice.country.CountryBaseService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EgpCountryService {

    private final EgpCountryRepository repository;
    private final CountryBaseService countryBaseService;

    public EgpCountryService(EgpCountryRepository repository, CountryBaseService countryBaseService) {
        this.repository = repository;
        this.countryBaseService = countryBaseService;
    }

    public Flux<EgpCountryDTO> getAll() {

        return repository.findAll()
                .flatMap(egpCountry -> {
                    Mono<CountryBase> countryBaseMono = countryBaseService.get(egpCountry.getCountryId());
                    return countryBaseMono.map(countryBase -> EgpCountryDTO
                            .builder()
                            .id(egpCountry.getId())
                            .countryName(countryBase.getName())
                            .isDefault(egpCountry.isDefault())
                            .build());
                });
    }

    public Mono<EgpCountryDTO> get(Integer id) {
        return repository.findById(id)
                .flatMap(egpCountry -> {
                    Mono<CountryBase> countryBaseMono = countryBaseService.get(egpCountry.getCountryId());
                    return countryBaseMono.map(countryBase -> EgpCountryDTO
                            .builder()
                            .id(egpCountry.getId())
                            .countryName(countryBase.getName())
                            .isDefault(egpCountry.isDefault())
                            .build());
                })
                .switchIfEmpty(Mono.empty());
    }


    public Mono<EgpCountryDTO> save(EgpCountryCreateDTO dto) {

        int countryId = dto.getCountryId();
        Mono<CountryBase> countryBaseMono = countryBaseService.get(countryId);
        EgpCountry egpCountry = new EgpCountry(dto.isDefault(), dto.getCountryId());

        Mono<EgpCountry> egpCountryMono;

        if (dto.isDefault()) {
            egpCountryMono = repository
                    .findByCountryId(dto.getCountryId())
                    .switchIfEmpty(
                            repository.findByDefault(dto.isDefault())
                                    .switchIfEmpty(repository.save(egpCountry))
                                    .flatMap(egpCountry1 -> Mono.empty())
                    );
        } else {
            egpCountryMono = repository
                    .findByCountryId(dto.getCountryId())
                    .switchIfEmpty(repository.save(egpCountry));
        }

        return egpCountryMono.flatMap(egpCountry1 ->
                        countryBaseMono.map(countryBase -> EgpCountryDTO
                                .builder()
                                .id(egpCountry.getId())
                                .countryName(countryBase.getName())
                                .isDefault(egpCountry.isDefault())
                                .build()))
                .switchIfEmpty(Mono.empty());

    }

    public Mono<EgpCountryDTO> update(EgpCountryUpdateDTO dto) {

        Mono<CountryBase> countryBaseMono = countryBaseService.get(dto.getCountryId());
        Mono<EgpCountry> egpCountryMono;
        if (dto.isDefault()) {

            Mono<EgpCountry> byDefault = repository.findByDefault(dto.isDefault());

            egpCountryMono = byDefault
                    .switchIfEmpty(
                            repository.findById(dto.getId()).flatMap(
                                    egpCountry ->
                                    {
                                        if (egpCountry.getId() != dto.getCountryId()) {
                                            return repository.findByCountryId(dto.getCountryId())
                                                    .switchIfEmpty(countryBaseMono.flatMap(countryBase -> {
                                                        egpCountry.setCountryId(dto.getCountryId());
                                                        egpCountry.setDefault(dto.isDefault());
                                                        return repository.save(egpCountry);
                                                    }));
                                        } else {
                                            return countryBaseMono.flatMap(countryBase -> {
                                                egpCountry.setCountryId(dto.getCountryId());
                                                egpCountry.setDefault(dto.isDefault());
                                                return repository.save(egpCountry);
                                            });
                                        }
                                    }));
        } else {

            egpCountryMono = repository.findById(dto.getId())
                    .flatMap(egpCountry ->
                    {
                        if (egpCountry.getId() != dto.getCountryId()) {
                            return repository.findByCountryId(dto.getCountryId())
                                    .switchIfEmpty(countryBaseMono.flatMap(countryBase -> {
                                        egpCountry.setCountryId(dto.getCountryId());
                                        egpCountry.setDefault(dto.isDefault());
                                        return repository.save(egpCountry);
                                    }));
                        } else {
                            return countryBaseMono.flatMap(countryBase -> {
                                egpCountry.setCountryId(dto.getCountryId());
                                egpCountry.setDefault(dto.isDefault());
                                return repository.save(egpCountry);
                            });
                        }
                    })
                    .switchIfEmpty(Mono.empty());
        }

        return egpCountryMono.flatMap(egpCountry -> countryBaseMono.switchIfEmpty(Mono.empty()).map(countryBase ->
                EgpCountryDTO
                        .builder()
                        .id(egpCountry.getId())
                        .countryName(countryBase.getName())
                        .isDefault(egpCountry.isDefault())
                        .build()));
    }

    public Mono<Void> delete(Integer id) {

        return repository.deleteById(id)
                .switchIfEmpty(Mono.empty());

    }

    public Mono<Integer> getDefaultCountryId() {

        return repository.findByDefault(true)
                .map(EgpCountry::getCountryId)
                .switchIfEmpty(Mono.empty());
    }
}

