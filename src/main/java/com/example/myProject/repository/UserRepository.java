package com.example.myProject.repository;

import com.example.myProject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //@EntityGraph --> fetch타입 무시가 가능하다.
    @EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    //unique
    User findByUsername(String username);

}
