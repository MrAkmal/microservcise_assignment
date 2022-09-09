package com.example.menu_service.menu;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuRepository extends R2dbcRepository<Menu,Integer> {

    Flux<Menu> findAllByRoleIdAndParentId(int roleId,int parentId);

    @Modifying
    Mono<Void> deleteAllByParentId(Integer parentId);

    Mono<Menu> findByParentIdAndKeywordBaseIdAndRoleId(Integer parentId, Integer keywordBaseId, Integer roleId);

    Mono<Menu> findByParentId(Integer parentId);

}
