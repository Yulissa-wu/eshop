package com.example.demo.service;

import com.example.demo.model.Customer;
import java.util.List;

public interface CustomerService {
    
    List<Customer> getAllCustomer();
    
    Customer getCustomerById(int cust_num);
    
    void saveCustomer(Customer customer);
    
    void updateCustomer(int cust_num, Customer updatedUser);
    
    void deleteCustomer(int cust_num);
}
