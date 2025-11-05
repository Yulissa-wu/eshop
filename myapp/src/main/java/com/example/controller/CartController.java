package com.example.controller;

import com.example.model.bean.Product;
import com.example.model.bean.CartItem;
import com.example.model.dao.ProductDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        if ("add".equals(action)) {
            // 添加商品到購物車
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            Product product = productDAO.getProductById(productId);
            if (product != null) {
                // 檢查購物車中是否已有此商品
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getProduct().getId() == productId) {
                        item.setQuantity(item.getQuantity() + quantity);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    cart.add(new CartItem(product, quantity));
                }
            }
            
        } else if ("update".equals(action)) {
            // 更新購物車商品數量
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    if (quantity > 0) {
                        item.setQuantity(quantity);
                    } else {
                        cart.remove(item);
                    }
                    break;
                }
            }
            
        } else if ("remove".equals(action)) {
            // 從購物車移除商品
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            cart.removeIf(item -> item.getProduct().getId() == productId);
        }
        
        session.setAttribute("cart", cart);
        response.sendRedirect("/cart");
    }
}
