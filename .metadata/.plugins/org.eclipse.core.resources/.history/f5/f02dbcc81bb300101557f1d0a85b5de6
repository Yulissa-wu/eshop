// =============================================
// 檔案名稱: ProductDAOImpl.java
// 功能說明: 商品DAO實作 (Spring JDBC Template)
// 建立時間: 2025
// =============================================

package com.example.demo.dao.impl;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品資料存取實作類別
 */
@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ==============================
    // 新增商品
    // ==============================
    @Override
    public int addProduct(Product product) {
        System.out.println("[LOG] 新增商品: " + product.getProductName());
        String sql = "INSERT INTO products (product_name, price, stock_quantity, category, description, image_url, created_at, updated_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                product.getProductName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getDescription(),
                product.getImageUrl(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
    }

    // ==============================
    // 根據ID查詢單個商品
    // ==============================
    @Override
    public Product getProductById(int productId) {
        System.out.println("[LOG] 查詢商品ID: " + productId);
        String sql = "SELECT * FROM products WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId},
                new BeanPropertyRowMapper<>(Product.class));
    }

    // ==============================
    // 查詢所有商品
    // ==============================
    @Override
    public List<Product> getAllProducts() {
        System.out.println("[LOG] 查詢所有商品");
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    // ==============================
    // 更新商品
    // ==============================
    @Override
    public int updateProduct(Product product) {
        System.out.println("[LOG] 更新商品ID: " + product.getProductId());
        String sql = "UPDATE products SET product_name=?, price=?, stock_quantity=?, category=?, description=?, image_url=?, updated_at=? "
                   + "WHERE product_id=?";
        return jdbcTemplate.update(
                sql,
                product.getProductName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getDescription(),
                product.getImageUrl(),
                Timestamp.valueOf(LocalDateTime.now()),
                product.getProductId()
        );
    }

    // ==============================
    // 刪除商品
    // ==============================
    @Override
    public int deleteProduct(int productId) {
        System.out.println("[LOG] 刪除商品ID: " + productId);
        String sql = "DELETE FROM products WHERE product_id=?";
        return jdbcTemplate.update(sql, productId);
    }
}
