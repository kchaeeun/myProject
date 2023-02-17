package com.example.myProject.model;
//import com.sun.istack.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//lombok 패키지가 깔려 있는 상태라 외부에서 꺼내쓸 수 있음
@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;

    @ManyToOne
    //어떤 column과 연결되어 있는지 작성(생략 ok)
    @JoinColumn(name = "user_id")//referencedColumnName = "id")
    @JsonIgnore //재귀적 호출 방지
    private User user;
}
