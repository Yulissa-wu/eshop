package com.example.demo.service;

import com.example.demo.model.OrderDetail;
import java.util.List;

public interface OrderDetailService {
    
    List<OrderDetail> getAllOrderDetail();
    
    OrderDetail getOrderDetailById(int detail_id);
    
    void saveOrderDetail(OrderDetail orderDetail);
    
    void updateOrderDetail(int detail_id, OrderDetail updatedUser);
    
    void deleteOrderDetail(int detail_id);

	



	
}
