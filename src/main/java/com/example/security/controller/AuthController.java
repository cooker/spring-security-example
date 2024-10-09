package com.example.security.controller;

import cn.hutool.jwt.JWTUtil;
import com.example.security.config.SecurityProperties;
import com.example.security.model.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * grant
 * 2024/10/8 17:46
 **/
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;

    @GetMapping("/login")
    public R<String> login(@RequestParam("username") String username) {
        // 创建认证令牌对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), "123456");
        // 执行用户认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = JWTUtil.createToken(Map.of("username", username), Map.of("user", authentication.getPrincipal()), securityProperties.getJwt().getKey().getBytes(StandardCharsets.UTF_8));
        return R.success(token);
    }


    @GetMapping("/logout")
    public R<String> logout(@RequestParam("token") String token) {
        return R.success("");
    }
}
