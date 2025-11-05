package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Customer;
import com.example.demo.model.User;

import java.util.List;

public interface CustomerService {
    
    List<Customer> getAllCustomer();
    
    Customer getCustomerById(Long id);
    
    void saveCustomer(Customer customer);
    
    void updateCustomer(Long id, Customer updatedUser);
    
    void deleteCustomer(Long id);
    
    Customer findByUser(User user);

	Customer findByEmail(String name);

	Customer findByCustomer(String customer);

	Customer findByCustomerName(String customerName);

	

    
}
