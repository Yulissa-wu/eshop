package com.example.controller;

import com.example.model.bean.Product;
import com.example.model.bean.Category;
import com.example.model.dao.ProductDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            // 顯示所有商品
            List<Product> products = productDAO.getAllProducts();
            List<Category> categories = productDAO.getAllCategories();
            
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/product-list.jsp").forward(request, response);
            
        } else if ("detail".equals(action)) {
            // 商品詳細頁面
            int productId = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.getProductById(productId);
            
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
            } else {
                response.sendRedirect("/products");
            }
            
        } else if ("category".equals(action)) {
            // 按分類篩選商品
            int categoryId = Integer.parseInt(request.getParameter("id"));
            List<Product> products = productDAO.getProductsByCategory(categoryId);
            List<Category> categories = productDAO.getAllCategories();
            
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.setAttribute("selectedCategory", categoryId);
            request.getRequestDispatcher("/product-list.jsp").forward(request, response);
        }
    }
}
