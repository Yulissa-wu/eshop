package com.example.demo.controller;

import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderdetail")
public class OrderDetailController {
    
    @Autowired
    private OrderDetailService orderdetailService;
    
    @GetMapping
    public String listOrderDetail(Model model) {
        model.addAttribute("orderdetail", orderdetailService.getAllOrderDetail());
        return "orderdetail";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("orderdetail", new OrderDetail());
        return "add-orderdetail";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int detail_id, Model model) {
    	OrderDetail orderdetail = orderdetailService.getOrderDetailById(detail_id);
        model.addAttribute("orderdetail", orderdetail);
        return "edit-orderdetail"; // 對應 /WEB-INF/views/edit-OrderDetail.html
    }
    
    @PostMapping("/update/{id}")
    public String updateOrderDetail(@PathVariable("id") int detail_id, @ModelAttribute("orderdetail") OrderDetail orderdetail) {
    	orderdetailService.updateOrderDetail(detail_id, orderdetail);
        return "redirect:/orderdetails";
    }
    
    @PostMapping("/save")
    public String saveOrderDetail(@ModelAttribute OrderDetail orderdetail) {
    	orderdetailService.saveOrderDetail(orderdetail);
        return "redirect:/orderdetails";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable("id") int detail_id) {
    	orderdetailService.deleteOrderDetail(detail_id);
        return "redirect:/orderdetail";
    }
}