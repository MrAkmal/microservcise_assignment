package com.example.menu_service.menu;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.menu_service.menu.dto.*;
import com.example.menu_service.utils.JWTUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class MenuService {

    private final String resourceServerUrl = "http://localhost:8585/v1/role";
    private final String keywordBaseUrl = "http://localhost:6060/v1/key_word_base/keyword_base_server";

    private final RestTemplate restTemplate;
    private final MenuRepository repository;

    @Autowired
    public MenuService(RestTemplate restTemplate, MenuRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public Mono<ResponseDTO<MenuDTO, String>> get(Integer menuId, String token) {


        String roleName = getRole(token);
        System.out.println("roleName = " + roleName);

        Mono<Menu> menuMono = repository.findById(menuId);

        return menuMono.flatMap(menu -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            RoleDTO role = null;
            try {
                role = restTemplate.exchange(RequestEntity.get(
                                new URI(resourceServerUrl + "/name/" + roleName)).headers(headers).build(),
                        RoleDTO.class).getBody();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            if (role == null) {
                return Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), new MenuDTO(), "Not Found"));
            }

            Mono<KeywordBaseDTO> objectMono = WebClient.builder().build()
                    .get()
                    .uri(keywordBaseUrl + "/" + menu.getKeywordBaseId())
                    .header(AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(KeywordBaseDTO.class);

            return objectMono.map(keywordBaseDTO -> {
                MenuDTO menuDTO = MenuDTO
                        .builder()
                        .id(menu.getId())
                        .genericName(keywordBaseDTO.getGenericName())
                        .wiseName(keywordBaseDTO.getWiseName())
                        .parentId(menu.getParentId())
                        .build();
                return new ResponseDTO<MenuDTO, String>(HttpStatus.OK.value(), menuDTO, "");
            }).switchIfEmpty(Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), null, "Not Found")));


        }).switchIfEmpty(Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), null, "Not Found")));
    }

    public Flux<ResponseDTO<MenuDTO, String>> getAll(String token) {

        return repository.findAll()
                .flatMap(menu -> get(menu.getId(), token));
    }

    @SneakyThrows
    public Flux<ResponseDTO<MenuDTO, String>> getMenu(String token, int parentId, int defaultCountryId) {

        String roleName = getRole(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        RoleDTO role = restTemplate.exchange(RequestEntity.get(
                        new URI(resourceServerUrl + "/name/" + roleName)).headers(headers).build(),
                RoleDTO.class).getBody();

        Flux<ResponseDTO<MenuDTO, String>> responseDTOFlux = repository.findAllByRoleIdAndParentId(role.getId(), parentId)
                .flatMap(menu -> {

                    Mono<KeywordBaseDTO> keywordBaseDTOMono = WebClient.builder().build()
                            .get()
                            .uri(keywordBaseUrl + "/country/" + menu.getKeywordBaseId() + "/" + defaultCountryId)
                            .header(AUTHORIZATION, "Bearer " + token)
                            .retrieve()
                            .bodyToMono(KeywordBaseDTO.class);


                    return keywordBaseDTOMono.map(keywordBaseDTO -> {
                        MenuDTO menuDTO = MenuDTO
                                .builder()
                                .id(menu.getId())
                                .genericName(keywordBaseDTO.getGenericName())
                                .wiseName(keywordBaseDTO.getWiseName())
                                .parentId(menu.getParentId())
                                .build();
                        return new ResponseDTO<MenuDTO, String>(HttpStatus.OK.value(), menuDTO, "");
                    }).switchIfEmpty(Mono.empty());
                }).switchIfEmpty(Flux.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), null, "Not Found")));

        return responseDTOFlux;
    }

    public Mono<ResponseDTO<MenuDTO, String>> create(MenuCreateDTO createDTO, String token) {


        String roleName = getRole(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(token, headers);
        System.out.println("token = " + token);

        RoleDTO role = restTemplate.exchange(resourceServerUrl + "/name/" + roleName,
                HttpMethod.GET, entity, RoleDTO.class).getBody();
        if (role == null) {
            return Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), null, "Not Found"));
        }

        Mono<KeywordBaseDTO> keywordBaseDTOMono = WebClient.builder().build()
                .get()
                .uri(keywordBaseUrl + "/" + createDTO.getKeywordId())
                .header(AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(KeywordBaseDTO.class);

        if (createDTO.getParentId() == 0) {

            return keywordBaseDTOMono.flatMap(keywordBaseDTO -> {
                Menu menu = Menu.builder()
                        .id(createDTO.getId())
                        .keywordBaseId(keywordBaseDTO.getId())
                        .roleId(role.getId())
                        .parentId(0)
                        .build();
                return repository.findByParentIdAndKeywordBaseIdAndRoleId(createDTO.getParentId(),
                                createDTO.getKeywordId(), createDTO.getRoleId())
                        .flatMap(menu1 -> Mono.just(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), new MenuDTO(), "Already Exist")))
                        .switchIfEmpty(repository.save(menu).flatMap(menu1 -> {
                            return Mono.just(new ResponseDTO<>(HttpStatus.OK.value(), new MenuDTO(menu.getId(), 0, keywordBaseDTO.getWiseName(), keywordBaseDTO.getGenericName()), ""));
                        }));

            });

        } else {
            return repository.findByParentId(createDTO.getParentId())
                    .flatMap(menu -> {
                        return keywordBaseDTOMono.flatMap(keywordBaseDTO -> {
                            Menu menu2 = Menu.builder()
                                    .id(createDTO.getId())
                                    .parentId(createDTO.getParentId())
                                    .keywordBaseId(keywordBaseDTO.getId())
                                    .roleId(role.getId())
                                    .parentId(0)
                                    .build();
                            return repository.findByParentIdAndKeywordBaseIdAndRoleId(createDTO.getParentId(),
                                            createDTO.getKeywordId(), createDTO.getRoleId())
                                    .flatMap(menu1 -> Mono.just(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), new MenuDTO(), "Already Exist")))
                                    .switchIfEmpty(repository.save(menu2).flatMap(menu1 -> {
                                        return Mono.just(new ResponseDTO<>(HttpStatus.OK.value(), new MenuDTO(menu.getId(), createDTO.getParentId(), keywordBaseDTO.getWiseName(), keywordBaseDTO.getGenericName()), ""));
                                    }));

                        });
                    })
                    .switchIfEmpty(Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), new MenuDTO(), "Not Found")));
        }

    }

    @Transactional
    public Mono<ResponseDTO<MenuDTO, String>> delete(Integer menuId) {

        return repository.findById(menuId)
                .flatMap(menu -> {
                    repository.deleteAllByParentId(menu.getId());
                    repository.deleteById(menuId);
                    return Mono.just(new ResponseDTO<>(HttpStatus.NO_CONTENT.value(), new MenuDTO(), ""));
                }).switchIfEmpty(Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), null, "Not Found")));
    }

    public Mono<ResponseDTO<MenuDTO, String>> update(MenuUpdateDTO updateDTO, String token) {

        return repository.findById(updateDTO.getId())
                .flatMap(menu -> {
                    return create(new MenuCreateDTO(
                                    updateDTO.getId(), updateDTO.getParentId(),
                                    updateDTO.getKeywordId(), updateDTO.getRoleId())
                            , token);
                }).switchIfEmpty(
                        Mono.just(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), null, "Not Found")));
    }


    private String getRole(String token) {

        JWTVerifier verifier = JWT.require(JWTUtils.getAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        return roles.get(0);

    }


}
