package com.example.demo.dao;

import com.example.demo.model.Product;
import java.util.List;

/**
 * 商品資料存取介面 (DAO)
 */
public interface ProductDAO {
    
    // 新增商品
    int addProduct(Product product);

    // 根據ID查詢商品
    Product getProductById(int productId);

    // 查詢所有商品
    List<Product> getAllProducts();

    // 更新商品
    int updateProduct(Product product);

    // 刪除商品
    int deleteProduct(int productId);
}
