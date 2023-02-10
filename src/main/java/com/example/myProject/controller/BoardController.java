package com.example.myProject.controller;

import com.example.myProject.model.Board;
import com.example.myProject.repository.BoardRepository;
import com.example.myProject.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//경로 지정
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    //직접 만든 BoardValidator 사용하기 - spring 기동 시 instance가 담김
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/list";
    }
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {  //필요한 경우에만 Parameter를 전달하면 id값을 사용하겠다
        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            //board 조회하기 위해 repository에서 가져오기
            Board board = boardRepository.findById(id).orElse(null);    //orElse() -> 조회 값이 없을 경우에는 null
            model.addAttribute("board", board);

        }
        return "board/form";
    }
    @PostMapping("/form")
    //Validated는 Valid의 기능이 포함됨.
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list"; ///list로의 redirect가 되면서 list에서 다시 한번 조회
    }
}
