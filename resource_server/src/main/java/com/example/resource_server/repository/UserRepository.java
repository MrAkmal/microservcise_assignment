package com.example.resource_server.repository;


import com.example.resource_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Modifying
    @Query(value = "update User u set u.password = :#{#user.password} where u.id = :#{#user.id}")
    void update(User user);
}
