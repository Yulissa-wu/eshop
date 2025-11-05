package com.example.demo.dao;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import java.util.List;

public interface OrderDAO {
    
    List<Order> findAll();
    
    Order findById(int ord_num);
//    Order findBynum(int cust_num);
//    Order findByType(int order_date);
//    Order findByLine(int county);
     
    void save(Order orders);
    
    void delete(int ord_num);
}
