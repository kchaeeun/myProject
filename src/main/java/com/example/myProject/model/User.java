package com.example.myProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore                 //권한 데이터 표시 X
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    //두 테이블 맵핑(User에서 Role을) --> List 사용
    //기본 값 NULL일 때의 오류 방지
    private List<Role> roles = new ArrayList<>();

    //양방향 mapping
    //JPA <-- Hibernate <-- Spring Data JPA
    //Cascade --> user 변경 시 Board도 변경을 하겠다.
    //orphanRemoval(defalut = false) --> 수정 전 데이터는 지운다.
    //--> 둘다 위험하지만 유용하다.(부모가 없는 데이터를 손쉽게 지울 수 있다.)
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)

    //fetch에서 EAGER를 사용하면 user, board의 내용을 다 가져옴
    //         LAZY를 사용하면 넘기길 원하는 것만 @JsonIgnore를 가지고 가져올 수 있음
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonIgnore         //board 정보 넘기지 x
    private List<Board> boards = new ArrayList<>();
}
