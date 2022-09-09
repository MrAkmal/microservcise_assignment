package com.example.menu_service.menu;

import com.example.menu_service.menu.dto.MenuCreateDTO;
import com.example.menu_service.menu.dto.MenuDTO;
import com.example.menu_service.menu.dto.MenuUpdateDTO;
import com.example.menu_service.menu.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/v1/menu")
public class MenuController {


    private final MenuService service;


    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public Mono<ResponseDTO<MenuDTO,String>> get(@PathVariable("id") Integer menuId){

        return service.get(menuId,getToken());
    }

    @GetMapping
    public Flux<ResponseDTO<MenuDTO,String>> getAll(){
        return service.getAll(getToken());

    }


    @GetMapping("/main_menu/{defaultCountryId}")
    public Flux<ResponseDTO<MenuDTO,String>> getMainMenu(@PathVariable Integer defaultCountryId){
        return service.getMenu(getToken(),0,defaultCountryId);

    }

    @GetMapping("/sub_menu/{parentId}/{defaultCountryId}")
    public Flux<ResponseDTO<MenuDTO,String>> getSubMenu(@PathVariable Integer parentId,@PathVariable Integer defaultCountryId){
        return service.getMenu(getToken(),parentId,defaultCountryId);
    }

    @PostMapping
    public Mono<ResponseDTO<MenuDTO,String>> create(@RequestBody MenuCreateDTO createDTO){
        return service.create(createDTO,getToken());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseDTO<MenuDTO,String>> delete(@PathVariable("id") Integer menuId){
        return service.delete(menuId);
    }

    @PutMapping
    public Mono<ResponseDTO<MenuDTO,String>> update(@RequestBody MenuUpdateDTO updateDTO){
        return service.update(updateDTO,getToken());
    }

    private String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        return authorizationHeader.substring(7);
    }



}
