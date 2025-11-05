package com.example.demo.service.impl;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服務實作類別
 * 封裝 DAO 層操作，提供 Controller 調用
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    // ==============================
    // 新增商品
    // ==============================
    @Override
    public int addProduct(Product product) {
        System.out.println("[LOG] Service: 新增商品 -> " + product.getProductName());
        return productDAO.addProduct(product);
    }

    // ==============================
    // 根據ID查詢商品
    // ==============================
    @Override
    public Product getProductById(int productId) {
        System.out.println("[LOG] Service: 查詢商品ID -> " + productId);
        return productDAO.getProductById(productId);
    }

    // ==============================
    // 查詢所有商品
    // ==============================
    @Override
    public List<Product> getAllProducts() {
        System.out.println("[LOG] Service: 查詢所有商品");
        return productDAO.getAllProducts();
    }

    // ==============================
    // 更新商品
    // ==============================
    @Override
    public int updateProduct(Product product) {
        System.out.println("[LOG] Service: 更新商品ID -> " + product.getProductId());
        return productDAO.updateProduct(product);
    }

    // ==============================
    // 刪除商品
    // ==============================
    @Override
    public int deleteProduct(int productId) {
        System.out.println("[LOG] Service: 刪除商品ID -> " + productId);
        return productDAO.deleteProduct(productId);
    }
}
