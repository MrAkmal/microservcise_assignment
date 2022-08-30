package com.example.financial_year_service.financialYear;

import com.example.financial_year_service.financialYear.dto.FinancialYearCreateDTO;
import com.example.financial_year_service.financialYear.dto.FinancialYearDTO;
import com.example.financial_year_service.financialYear.dto.FinancialYearUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FinancialYearService {

    private final FinancialYearRepository repository;
    private final FinancialYearMapper mapper;

    @Autowired
    public FinancialYearService(FinancialYearRepository repository, FinancialYearMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public Mono<FinancialYear> save(FinancialYearCreateDTO dto) {


        if (dto.isDefault()) {
            Mono<FinancialYear> byDefaultIsTrue = repository.findByDefault(dto.isDefault());

            return byDefaultIsTrue.switchIfEmpty(repository.findByYear(dto.getYear()).switchIfEmpty(repository.save(mapper.fromCreateDTO(dto))));

        }

        return repository.findByYear(dto.getYear()).switchIfEmpty(repository.save(mapper.fromCreateDTO(dto)));

    }


    public Mono<Void> delete(int financialYear) {

        return repository.deleteById(financialYear);
    }


    public Mono<FinancialYear> update(FinancialYearUpdateDTO dto) {


        if (dto.isDefault()) {
            Mono<FinancialYear> byDefaultIsTrue = repository.findByDefault(dto.isDefault());

            return byDefaultIsTrue
                    .switchIfEmpty(repository.findByYear(dto.getYear()).switchIfEmpty(repository.save(mapper.fromUpdateDTO(dto))))
                    .flatMap(financialYear -> {
                        if (financialYear.getId() == dto.getId()) return repository.save(mapper.fromUpdateDTO(dto));
                        else return Mono.empty();
                    });
        }

        return repository.findByYear(dto.getYear())
                .switchIfEmpty(repository.save(mapper.fromUpdateDTO(dto)))
                .flatMap(financialYear -> {
                    if (financialYear.getId() == dto.getId()) return repository.save(mapper.fromUpdateDTO(dto));
                    else return Mono.empty();
                });


    }


    public Mono<FinancialYearDTO> get(int financialYearId) {

        return repository.findById(financialYearId).map(mapper::toDTO);

    }


    public Flux<FinancialYearDTO> getAll() {


        return repository.findAll(Sort.by(Sort.Direction.ASC, "year")).map(mapper::toDTO);


//        Mono<List<FinancialYearDTO>> mono = all.collectList().map(mapper::toDTO);

//        mono.block().stream().forEach(System.out::println);

//        return mono.flatMapMany(Flux::fromIterable);
    }


    public Flux<FinancialYearDTO> getAllBySort(String fieldName, boolean type) {

        if (type) return repository.findAll(Sort.by(Sort.Direction.ASC, fieldName)).map(mapper::toDTO);
        return repository.findAll(Sort.by(Sort.Direction.DESC, fieldName)).map(mapper::toDTO);

    }


}
