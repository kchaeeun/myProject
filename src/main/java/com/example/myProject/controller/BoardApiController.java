package com.example.myProject.controller;

import com.example.myProject.model.Board;
import com.example.myProject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String content) {               //id가 아닌 titler과 content를 기준으로 데이터 출력
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {                 //title과 content가 전달이 안됐을 경우
            return repository.findAll();        //전체 데이터 조회
        } else {
            return repository.findByTitleOrContent(title, content);                       //BoardRepository에서 만든 메서드 사용, title혹은 content에 맞는 데이터가 리턴
        }
    }
    // end::get-aggregate-root[]

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")             //작성한 데이터가 보여짐, id로 구분 가능
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());        //Board 클래스에서 가져온 title,content
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @Secured("ROLE_ADMIN")          // 해당 권한자만 delete 사용 가능 (취약점 해결) --> 로그인 하라는 문자 출력
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}