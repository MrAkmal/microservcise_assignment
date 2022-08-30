package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.FinancialYearCreateDTO;
import com.example.backend_as_frontend.entity.FinancialYear;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
public class FinancialYearService {

    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/financial_year";


    public FinancialYearService(WebClient webClient) {
        this.webClient = webClient;
    }

    public FinancialYearCreateDTO get(Integer id) {

        Mono<FinancialYear> financialYearFlux = webClient.get()
                .uri(baseURI + "/" + id)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(FinancialYear.class);

        FinancialYear block = financialYearFlux.block();

        String year = block.getYear();

        String fromYear = year.substring(0, year.indexOf('-'));
        String yearTo = year.substring(year.indexOf('-') + 1);

        System.out.println("fromYear = " + fromYear);
        System.out.println("yearTo = " + yearTo);


        return new FinancialYearCreateDTO(block.getId(), Integer.valueOf(fromYear), Integer.valueOf(yearTo), block.isDefault());
    }


    public List<FinancialYear> getAll() {

        Mono<List<FinancialYear>> entity = webClient.get()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(FinancialYear.class)
                .collectList();

        List<FinancialYear> block = entity.block();
        return block;

    }

//    public static void main(String[] args) {
//
//        Mono<List<FinancialYear>> entity = WebClient.builder().build().get()
//                .uri("http://localhost:9595/v1/financial_year")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .retrieve()
//                .bodyToFlux(FinancialYear.class)
//                .collectList();
//
//        List<FinancialYear> block = entity.block();
//        block.forEach(System.out::println);
//
//    }


    public List<FinancialYear> getAllBySort(String fieldName, boolean type) {



        Mono<List<FinancialYear>> entity = WebClient.builder().build().get()
                .uri(baseURI + "/sort?fieldName=" + fieldName + "&type=" + type)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(FinancialYear.class)
                .collectList();


        List<FinancialYear> block = entity.block();
        return block;

    }


    public void save(FinancialYearCreateDTO dto) {


        if (dto.getYearTo() - dto.getYearFrom() != 1) throw new RuntimeException("Wrong difference between years");

        String financialYearDifference = dto.getYearFrom() + "-" + dto.getYearTo();

        FinancialYear financialYear = new FinancialYear(dto.getId(), financialYearDifference, dto.isDefault());

        System.out.println("financialYear = " + financialYear);

        Mono<FinancialYear> mono = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(financialYear), FinancialYear.class)
                .retrieve()
                .bodyToMono(FinancialYear.class);

        System.out.println("mono = " + mono.block());
    }


//    public static void main(String[] args) {
//
//
//        FinancialYear financialYear = new FinancialYear(8, "6060-6061",true);
//
//        System.out.println("financi   alYear = " + financialYear);
//
//        Mono<FinancialYear> mono = WebClient.builder().build().put()
//                .uri("http://localhost:3030/v1/financial_year")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(financialYear), FinancialYear.class)
//                .retrieve()
//                .bodyToMono(FinancialYear.class);
//
//        System.out.println("mono = " + mono.block());
//
//    }


    public void update(FinancialYearCreateDTO dto) {

        if (dto.getYearTo() - dto.getYearFrom() != 1) throw new RuntimeException("Wrong difference between years");

        String financialYearDifference = dto.getYearFrom() + "-" + dto.getYearTo();

        FinancialYear financialYear = new FinancialYear(dto.getId(), financialYearDifference, dto.isDefault());

        Mono<FinancialYear> mono = webClient.put()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(financialYear), FinancialYear.class)
                .retrieve()
                .bodyToMono(FinancialYear.class);

        System.out.println("Update : mono.block() = " + mono.block());

    }


    public void delete(Integer id) {
        FinancialYearCreateDTO financialYear = get(id);

        if (financialYear != null) {

            Mono<Void> mono = webClient.delete()
                    .uri(baseURI + "/" + id)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(AUTHORIZATION, getToken())
                    .retrieve()
                    .bodyToMono(Void.class);
            System.out.println("mono = " + mono.block());
        }
    }


}
