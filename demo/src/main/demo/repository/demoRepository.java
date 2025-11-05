
package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 產品資料庫操作介面
 * 繼承 JpaRepository 獲得基本 CRUD 功能
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}

