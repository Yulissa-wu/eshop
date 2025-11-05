package com.example.demo.dao;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductDAO {
    
    List<Product> findAll();
    
    Product findById(Long id);
//    Product findByName(int prod_name);
//    Product findByType(int prod_type);
//    Product findByLine(int prod_line);
//    Product findByPrice(int pro_price);
    
    void save(Product products);
    
    void delete(Long id);
}
