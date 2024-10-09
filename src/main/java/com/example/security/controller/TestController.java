package com.example.security.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * grant
 * 2024/10/9 09:51
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/v1/123")
    @RolesAllowed("user")
    public String testV1_123() {
        return "testV1_123";
    }
}
