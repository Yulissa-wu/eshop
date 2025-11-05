package com.example.demo.dao;

import com.example.demo.model.Customer;
import com.example.demo.model.OrderDetail;
import java.util.List;

public interface OrderDetailDAO {
    
    List<OrderDetail> findAll();
    
    OrderDetail findById(int detail_id);
//    OrderDetail findBynum1(int ord_num);
//    OrderDetail findBynum2(int prod_num);
//    OrderDetail findByqty(int ord_qty);
//    OrderDetail findByprice(int ord_price);
    
     
    void save(OrderDetail orderDetail);
    
    void delete(int cust_num);


}
