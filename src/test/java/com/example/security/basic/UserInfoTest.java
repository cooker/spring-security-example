package com.example.security.basic;

import cn.hutool.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * grant
 * 2024/10/9 14:03
 **/
public class UserInfoTest {
    @Test
    public void password() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
        String token = JWTUtil.createToken(Map.of("body", "123"), "123456".getBytes(StandardCharsets.UTF_8));
        System.out.println(token);
    }
}
