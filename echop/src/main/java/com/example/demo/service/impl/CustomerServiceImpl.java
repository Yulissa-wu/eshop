package com.example.demo.service.impl;

import com.example.demo.dao.CustomerDAO;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO CustomerRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return CustomerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int cust_num) {
        return CustomerRepository.findById(cust_num);
    }

    @Override
    public void saveCustomer(Customer customer) {
    	CustomerRepository.save(customer);
    }

    @Override
    public void updateCustomer(int cust_num, Customer updatedUser) {
    	Customer existingUser = CustomerRepository.findById(cust_num);
//        if (existingUser != null) {
//            existingUser.setName(updatedUser.getName());
//            existingUser.setEmail(updatedUser.getEmail());
//            userRepository.save(existingUser);
//        }
    }

    @Override
    public void deleteCustomer(int cust_num) {
    	CustomerRepository.delete(cust_num);
    }
}
