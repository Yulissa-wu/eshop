// =============================================
// 商品分類控制器
// 創建時間: 2024
// 功能: 處理商品分類相關的頁面路由
// =============================================

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品分類控制器
 * 處理商品分類相關的頁面路由
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    /**
     * 顯示商品分類頁面
     * @param model 模型對象
     * @return 商品分類頁面視圖名稱
     */
    @GetMapping
    public String showCategories(Model model) {
        // 記錄日誌
        System.out.println("顯示商品分類頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "商品分類");
        model.addAttribute("currentPage", "categories");
        
        return "categories"; // 對應 /WEB-INF/views/categories.html
    }
    
    /**
     * 顯示特定分類的商品
     * @param categoryId 分類ID
     * @param model 模型對象
     * @return 分類商品頁面視圖名稱
     */
    @GetMapping("/{categoryId}")
    public String showCategoryProducts(@PathVariable("categoryId") String categoryId, Model model) {
        // 記錄日誌
        System.out.println("顯示分類商品頁面 - 分類ID: " + categoryId);
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "分類商品");
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("currentPage", "category-products");
        
        return "category-products"; // 對應 /WEB-INF/views/category-products.html
    }
}
