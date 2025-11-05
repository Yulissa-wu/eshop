package com.example.demo.dao.impl;

import com.example.demo.dao.OrderDetailDAO;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<OrderDetail> findAll() {
        return getCurrentSession().createQuery("FROM OrderDetail", OrderDetail.class).list();
    }

    @Override
    public OrderDetail findById(int detail_id) {
        return getCurrentSession().get(OrderDetail.class, detail_id);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        getCurrentSession().saveOrUpdate(orderDetail);
    }

    @Override
    public void delete(int detail_id) {
    	OrderDetail orderDetail = getCurrentSession().get(OrderDetail.class,detail_id);
        if (orderDetail != null) {
            getCurrentSession().delete(orderDetail);
        }
    }




}
