package com.example.demo.controller;

import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品API控制器
 * 處理商品相關的API請求
 */
@RestController
@RequestMapping("/api")
public class ProductApiController {
    
    /**
     * 測試API
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "API測試成功");
        return ResponseEntity.ok(result);
    }
    
    /**
     * 獲取所有商品
     */
    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "newest") String sort) {
        
        System.out.println("API請求 - 獲取商品列表: page=" + page + ", pageSize=" + pageSize);
        
        Map<String, Object> result = new HashMap<>();
        try {
            // 模擬商品數據（不依賴數據庫）
            var convertedProducts = new java.util.ArrayList<Map<String, Object>>();
            
            // 添加模擬商品
            Map<String, Object> product1 = new HashMap<>();
            product1.put("product_id", 1L);
            product1.put("product_name", "iPhone 15 Pro");
            product1.put("price", 999.99);
            product1.put("main_image", "/resources/img/placeholder.jpg");
            product1.put("category_name", "手機");
            product1.put("stock_quantity", 50);
            product1.put("status", "active");
            product1.put("description", "最新款iPhone，配備A17 Pro芯片");
            convertedProducts.add(product1);
            
            Map<String, Object> product2 = new HashMap<>();
            product2.put("product_id", 2L);
            product2.put("product_name", "MacBook Pro 16");
            product2.put("price", 2499.99);
            product2.put("main_image", "/resources/img/placeholder.jpg");
            product2.put("category_name", "筆記本電腦");
            product2.put("stock_quantity", 30);
            product2.put("status", "active");
            product2.put("description", "高性能筆記本電腦，適合專業用戶");
            convertedProducts.add(product2);
            
            Map<String, Object> product3 = new HashMap<>();
            product3.put("product_id", 3L);
            product3.put("product_name", "Nike Air Max 270");
            product3.put("price", 150.00);
            product3.put("main_image", "/resources/img/placeholder.jpg");
            product3.put("category_name", "運動鞋");
            product3.put("stock_quantity", 100);
            product3.put("status", "active");
            product3.put("description", "舒適的運動鞋，適合日常穿著");
            convertedProducts.add(product3);
            
            Map<String, Object> product4 = new HashMap<>();
            product4.put("product_id", 4L);
            product4.put("product_name", "Sony WH-1000XM5");
            product4.put("price", 399.99);
            product4.put("main_image", "/resources/img/placeholder.jpg");
            product4.put("category_name", "音頻設備");
            product4.put("stock_quantity", 25);
            product4.put("status", "active");
            product4.put("description", "高品質降噪耳機");
            convertedProducts.add(product4);
            
            result.put("success", true);
            result.put("products", convertedProducts);
            result.put("total_pages", 1);
            result.put("page", page);
            result.put("total_count", convertedProducts.size());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("獲取商品列表失敗: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "獲取商品列表失敗");
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 獲取單個商品詳情
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        System.out.println("API請求 - 獲取商品詳情: id=" + id);
        
        Map<String, Object> result = new HashMap<>();
        try {
            // 模擬商品詳情數據
            Map<String, Object> product = new HashMap<>();
            product.put("product_id", id);
            product.put("product_name", "商品 " + id);
            product.put("price", 99.99);
            product.put("main_image", "/resources/img/placeholder.jpg");
            product.put("category_name", "分類");
            product.put("stock_quantity", 100);
            product.put("status", "active");
            product.put("description", "商品描述");
            
            result.put("success", true);
            result.put("product", product);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("獲取商品詳情失敗: " + e.getMessage());
            result.put("success", false);
            result.put("message", "獲取商品詳情失敗");
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 獲取商品分類
     */
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories() {
        System.out.println("API請求 - 獲取商品分類");
        
        Map<String, Object> result = new HashMap<>();
        try {
            // 模擬分類數據
            var categories = new java.util.ArrayList<Map<String, Object>>();
            
            Map<String, Object> category1 = new HashMap<>();
            category1.put("category_id", 1);
            category1.put("category_name", "手機");
            categories.add(category1);
            
            Map<String, Object> category2 = new HashMap<>();
            category2.put("category_id", 2);
            category2.put("category_name", "筆記本電腦");
            categories.add(category2);
            
            Map<String, Object> category3 = new HashMap<>();
            category3.put("category_id", 3);
            category3.put("category_name", "運動鞋");
            categories.add(category3);
            
            Map<String, Object> category4 = new HashMap<>();
            category4.put("category_id", 4);
            category4.put("category_name", "音頻設備");
            categories.add(category4);
            
            result.put("success", true);
            result.put("categories", categories);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("獲取分類失敗: " + e.getMessage());
            result.put("success", false);
            result.put("message", "獲取分類失敗");
            return ResponseEntity.status(500).body(result);
        }
    }
}
