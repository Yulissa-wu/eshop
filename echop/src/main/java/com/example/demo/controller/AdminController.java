// =============================================
// 管理員控制器
// 創建時間: 2024
// 功能: 處理管理員相關的頁面路由
// =============================================

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理員控制器
 * 處理管理員相關的頁面路由
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    /**
     * 顯示管理員儀表板
     * @param model 模型對象
     * @return 管理員儀表板視圖名稱
     */
    @GetMapping
    public String showAdminDashboard(Model model) {
        // 記錄日誌
        System.out.println("顯示管理員儀表板");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "管理員儀表板");
        model.addAttribute("currentPage", "dashboard");
        
        return "admin-dashboard"; // 可以創建對應的視圖
    }
    
    /**
     * 顯示管理員商品管理頁面
     * @param model 模型對象
     * @return 管理員商品管理頁面視圖名稱
     */
    @GetMapping("/products")
    public String showAdminProducts(Model model) {
        // 記錄日誌
        System.out.println("顯示管理員商品管理頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "商品管理");
        model.addAttribute("currentPage", "products");
        
        return "admin-products"; // 對應 /WEB-INF/views/admin-products.html
    }
    
    /**
     * 顯示管理員分類管理頁面
     * @param model 模型對象
     * @return 管理員分類管理頁面視圖名稱
     */
    @GetMapping("/categories")
    public String showAdminCategories(Model model) {
        // 記錄日誌
        System.out.println("顯示管理員分類管理頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "分類管理");
        model.addAttribute("currentPage", "categories");
        
        return "admin-categories"; // 可以創建對應的視圖
    }
    
    /**
     * 顯示管理員訂單管理頁面
     * @param model 模型對象
     * @return 管理員訂單管理頁面視圖名稱
     */
    @GetMapping("/orders")
    public String showAdminOrders(Model model) {
        // 記錄日誌
        System.out.println("顯示管理員訂單管理頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "訂單管理");
        model.addAttribute("currentPage", "orders");
        
        return "admin-orders"; // 可以創建對應的視圖
    }
    
    /**
     * 顯示管理員客戶管理頁面
     * @param model 模型對象
     * @return 管理員客戶管理頁面視圖名稱
     */
    @GetMapping("/customers")
    public String showAdminCustomers(Model model) {
        // 記錄日誌
        System.out.println("顯示管理員客戶管理頁面");
        
        // 添加頁面屬性
        model.addAttribute("pageTitle", "客戶管理");
        model.addAttribute("currentPage", "customers");
        
        return "admin-customers"; // 可以創建對應的視圖
    }
}
