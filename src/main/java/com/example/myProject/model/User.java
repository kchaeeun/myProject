package com.example.myProject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//lombok에 사용하는 어노테이션 일부를 작성하지 않아 .setPassword 등의 어노테이션에 오류 발생
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    //두 테이블 맵핑(User에서 Role을) --> List 사용
    //기본 값 NULL일 때의 오류 방지
    private List<Role> roles = new ArrayList<>();
}
