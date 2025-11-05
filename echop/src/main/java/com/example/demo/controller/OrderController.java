package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String listOrder(Model model) {
        model.addAttribute("order", orderService.getAllOrder());
        return "order";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        return "add-order";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int ord_num, Model model) {
    	Order order = orderService.getOrderById(ord_num);
        model.addAttribute("order", order);
        return "edit-order"; // 對應 /WEB-INF/views/edit-user.html
    }
    
    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") int id, @ModelAttribute("order") Order order) {
    	orderService.updateOrder(id, order);
        return "redirect:/order";
    }
    
    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order) {
    	orderService.saveOrder(order);
        return "redirect:/order";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") int ord_num) {
    	orderService.deleteOrder(ord_num);
        return "redirect:/order";
    }
}