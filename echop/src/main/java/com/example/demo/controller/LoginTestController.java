package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登錄測試控制器
 */
@Controller
public class LoginTestController {
    
    @GetMapping("/login-test")
    public String showLoginTest() {
        return "login-test";
    }
}
