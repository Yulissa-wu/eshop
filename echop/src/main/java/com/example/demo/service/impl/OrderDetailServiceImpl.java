package com.example.demo.service.impl;

import com.example.demo.dao.OrderDetailDAO;
import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailDAO OrderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetail() {
        return OrderDetailRepository.findAll();
    }

    @Override
    public OrderDetail getOrderDetailById(int detail_id) {
        return OrderDetailRepository.findById(detail_id);
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
    	OrderDetailRepository.save(orderDetail);
    }

    @Override
    public void updateOrderDetail(int detail_id, OrderDetail updatedOrderDetail) {
    	OrderDetail existingOrderDetail = OrderDetailRepository.findById(detail_id);
//        if (existingUser != null) {
//            existingUser.setName(updatedUser.getName());
//            existingUser.setEmail(updatedUser.getEmail());
//            userRepository.save(existingUser);
//        }
    }

    @Override
    public void deleteOrderDetail(int detail_id) {
    	OrderDetailRepository.delete(detail_id);
   }
   
}     


