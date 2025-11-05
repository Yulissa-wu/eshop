package com.example.demo.dao;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import java.util.List;

public interface CustomerDAO {
    
    List<Customer> findAll();
    
//    Customer findByName(int cust_name);
    Customer findById(int cust_num);
     
    void save(Customer customers);
    
    void delete(int cust_num);
}
