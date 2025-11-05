// =============================================
// 路由測試控制器
// 創建時間: 2024
// 功能: 測試所有路由是否正常工作
// =============================================

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路由測試控制器
 * 用於測試所有頁面路由是否正常工作
 */
@Controller
public class RouteTestController {
    
    /**
     * 測試首頁路由
     * @param model 模型對象
     * @return 首頁視圖
     */
    @GetMapping("/test")
    public String testHome(Model model) {
        System.out.println("測試首頁路由 - /test");
        model.addAttribute("message", "路由測試成功！");
        return "home"; // 對應 /WEB-INF/views/home.html
    }
    
    /**
     * 測試商品展示路由
     * @param model 模型對象
     * @return 商品展示視圖
     */
    @GetMapping("/test-products")
    public String testProducts(Model model) {
        System.out.println("測試商品展示路由 - /test-products");
        model.addAttribute("message", "商品展示路由測試成功！");
        return "product-catalog"; // 對應 /WEB-INF/views/product-catalog.html
    }
    
    /**
     * 測試認證路由
     * @param model 模型對象
     * @return 認證視圖
     */
    @GetMapping("/test-auth")
    public String testAuth(Model model) {
        System.out.println("測試認證路由 - /test-auth");
        model.addAttribute("message", "認證路由測試成功！");
        return "auth"; // 對應 /WEB-INF/views/auth.html
    }
}


