package com.example.myProject.controller;

import com.example.myProject.model.Board;
import com.example.myProject.model.User;
import com.example.myProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
//로그 찍기 위한 어노테이션
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> all() {
        List<User> users = repository.findAll();
        // 첫번째 유저 데이터 출력, log 출력
        log.debug("getBoards().size() 호출전");
        // 첫번째 파라미터의 정보가 {}에 담김
        // 그 사용자의 데이터만 불러옴
        log.debug("getBoards().size() : {}", users.get(0).getBoards().size());
        log.debug("getBoards().size() 호출후");
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")             //작성한 데이터가 보여짐, id로 구분 가능
    User one(@PathVariable Long id) {
        //user수에 맞게 sql문 출력
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());        //User 클래스에서 가져온 title,content
//                    user.setContent(newUser.getContent());
//                    기존 board를 새로운 데이터로 변경
//                    user.setBoards(newUser.getBoards());
                    //  기존 list clear 후에 새로운 list 추가
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());

                    //user의 정보 담기(key값 정해주기)
                    for(Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}