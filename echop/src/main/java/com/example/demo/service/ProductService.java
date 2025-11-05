package com.example.demo.service;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductService {
    
    List<Product> getAllProduct();
    
    Product getProductById(Long id);
    
    void saveProduct(Product product);
    
    void updateProduct(Long id, Product updatedProduct);
    
    void deleteProduct(Long id);

}
