package com.example.demo.controller;

import com.example.demo.model.Customer;

import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public String listCustomer(Model model) {
        model.addAttribute("customer", customerService.getAllCustomer());
        return "customer";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
    	Customer customer = customerService.getCustomerById(id);
        model.addAttribute("Customer", customer);
        return "edit-customer"; // 對應 /WEB-INF/views/edit-Customer.html
    }
    
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @ModelAttribute("customer") Customer customer) {
    	customerService.updateCustomer(id, customer);
        return "redirect:/customer";
    }
    
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
    	customerService.saveCustomer(customer);
        return "redirect:/customer";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
    	customerService.deleteCustomer(id);
        return "redirect:/customer";
    }
}