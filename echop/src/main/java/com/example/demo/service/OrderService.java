package com.example.demo.service;

import com.example.demo.model.Order;
import java.util.List;

public interface OrderService {
    
    List<Order> getAllOrder();
    
    Order getOrderById(int ord_num);
    
    void saveOrder(Order order);
    
    void updateOrder(int ord_num, Order updatedUser);
    
    void deleteOrder (int ord_num);

		
}
