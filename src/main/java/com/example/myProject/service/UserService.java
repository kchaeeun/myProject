package com.example.myProject.service;

import com.example.myProject.model.Role;
import com.example.myProject.model.User;
import com.example.myProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//test 작성에 유용
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        //사용자가 전달한 password를 encoder을 이용해 encode를 하고 암호화를 한다.
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        //저장
        user.setPassword(encodedPassword);
        //회원가입 시 기본적으로 활성화된 상태
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        //role에 어떤 권한을 줄 건지 저장 --> user_role table에 알아서 값이 저장
        user.getRoles().add(role);
        return userRepository.save(user);

    }

}
