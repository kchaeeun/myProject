package com.example.myProject.validator;

import com.example.myProject.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;          //

//denpendency 사용 가능
@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;             //클래스 Board로 형변환
        if(StringUtils.isEmpty(b.getContent())) {       //Board의 content 값이 비어있는 지 체크
            errors.rejectValue("content", "key","내용을 입력하세요");
        }
    }
}
