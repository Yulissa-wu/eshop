// =============================================
// 認證控制器
// 創建時間: 2024
// 功能: 處理用戶認證相關的頁面路由
// =============================================

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 認證控制器
 * 處理用戶登錄、註冊等認證相關的頁面路由
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    
    // =============================================
    // 頁面路由方法
    // =============================================
    
    /**
     * 顯示登錄頁面
     * @param model 模型對象
     * @return 登錄頁面視圖名稱
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // 記錄日誌
        System.out.println("顯示登錄頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "用戶登錄");
        model.addAttribute("isLogin", true);
        
        return "auth"; // 對應 /WEB-INF/views/auth.html
    }
    
    /**
     * 顯示註冊頁面
     * @param model 模型對象
     * @return 註冊頁面視圖名稱
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        // 記錄日誌
        System.out.println("顯示註冊頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "用戶註冊");
        model.addAttribute("isLogin", false);
        
        return "auth"; // 對應 /WEB-INF/views/auth.html
    }
    
    /**
     * 處理根路徑重定向到登錄頁面
     * @return 重定向到登錄頁面
     */
    @GetMapping
    public String redirectToLogin() {
        // 記錄日誌
        System.out.println("重定向到登錄頁面");
        
        return "redirect:/auth/login";
    }
}