package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登錄調試控制器
 */
@Controller
public class DebugController {
    
    @GetMapping("/debug-login")
    public String showDebugLogin() {
        return "debug-login";
    }
}

