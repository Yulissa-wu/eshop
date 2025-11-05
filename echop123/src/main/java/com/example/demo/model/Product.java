// =============================================
// 檔案名稱: Product.java
// 功能說明: 商品模型（純 Java，不依賴 JPA）
// 建立時間: 2025
// =============================================

package com.example.demo.model;

import java.time.LocalDateTime;

/**
 * 商品模型類別
 * 對應資料庫 products 表
 */
public class Product {

    // 商品編號
    private Integer productId;

    // 商品名稱
    private String productName;

    // 價格
    private Double price;

    // 庫存數量
    private Integer stockQuantity;

    // 商品分類
    private String category;

    // 商品描述
    private String description;

    // 商品圖片路徑
    private String imageUrl;

    // 建立時間
    private LocalDateTime createdAt;

    // 更新時間
    private LocalDateTime updatedAt;

    // ==============================
    // Getter / Setter 區
    // ==============================

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        System.out.println("[LOG] 設定商品ID：" + productId);
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        System.out.println("[LOG] 設定商品名稱：" + productName);
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        System.out.println("[LOG] 設定商品價格：" + price);
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        System.out.println("[LOG] 設定庫存數量：" + stockQuantity);
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        System.out.println("[LOG] 設定商品分類：" + category);
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        System.out.println("[LOG] 設定商品描述：" + description);
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        System.out.println("[LOG] 設定商品圖片：" + imageUrl);
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        System.out.println("[LOG] 設定建立時間：" + createdAt);
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        System.out.println("[LOG] 設定更新時間：" + updatedAt);
        this.updatedAt = updatedAt;
    }

    // ==============================
    // toString() 方便日誌輸出
    // ==============================
    @Override
    public String toString() {
        return "[LOG] Product{"
                + "productId=" + productId
                + ", productName='" + productName + '\''
                + ", price=" + price
                + ", stockQuantity=" + stockQuantity
                + ", category='" + category + '\''
                + ", description='" + description + '\''
                + ", imageUrl='" + imageUrl + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }
}
