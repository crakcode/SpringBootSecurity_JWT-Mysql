package com.example.demo;

import lombok.RequiredArgsConstructor;

import com.example.demo.JwtTokenProvider;
import com.example.demo.User;
import com.example.demo.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody User user) {
        return userRepository.save(User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        System.out.println(member.getPassword());
        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
    }
    @PostMapping("/hello")
    public String hello(@RequestBody User user) {
        return "hellowolrd";
    }

    
    //X-AUTH-TOKEN 을 넣어야 정상적이게 작동한다. 
    @GetMapping("/user/hello")
    public String user(@RequestBody User user) {
        return "www";
    }

}
