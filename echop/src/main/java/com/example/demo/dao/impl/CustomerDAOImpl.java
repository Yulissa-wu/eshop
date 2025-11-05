package com.example.demo.dao.impl;

import com.example.demo.dao.CustomerDAO;
import com.example.demo.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Customer> findAll() {
		return getCurrentSession().createQuery("FROM Customer", Customer.class).list();
	}

	@Override
	public Customer findById(int cust_num) {
		return getCurrentSession().get(Customer.class, cust_num);
	}

	@Override
	public void save(Customer customer) {
		getCurrentSession().saveOrUpdate(customer);
		
	}

	@Override
	public void delete(int cust_num) {
    	Customer customer = getCurrentSession().get(Customer.class,cust_num);
        if (customer != null) {
            getCurrentSession().delete(customer);
        }
		
	}


}
