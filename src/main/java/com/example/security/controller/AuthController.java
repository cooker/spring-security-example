package com.example.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * grant
 * 2024/10/8 17:46
 **/
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam("username") String username) {
        // 创建认证令牌对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), "123456");
        // 执行用户认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return Map.of("body", authentication);
    }
}
