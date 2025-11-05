
package com.example.demo.controller;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.constant.ViewNames;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/products")
public class ProductViewController {
   private final ProductRepository productRepository;
	// 建構子注入 ProductRepository
   public ProductViewController(ProductRepository productRepository) {
       this.productRepository = productRepository;
   }
   /**
    * 顯示產品列表與新增表單
    */
   @GetMapping
   public String showProductList(Model model) {
       model.addAttribute("products", productRepository.findAll());
       model.addAttribute("product", new Product());
       return ViewNames.VIEW_PRODUCTS;
   }
   /**
    * 處理新增產品請求
    */
   @PostMapping
   public String createProduct(
           @Valid @ModelAttribute("product") Product product,
           BindingResult bindingResult,
           Model model) {
       if (bindingResult.hasErrors()) {
           model.addAttribute("products", productRepository.findAll());
           return ViewNames.VIEW_PRODUCTS;
       }
       productRepository.save(product);
       return ViewNames.REDIRECT_PRODUCTS;
   }
}

