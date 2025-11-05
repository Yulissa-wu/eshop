package com.example.demo.dao.impl;

import com.example.demo.dao.CartDAO;
import com.example.demo.service.CartService;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Customer;
import com.example.demo.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CartDAOImpl implements CartDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private CartService CartService;

    @PersistenceContext
    private EntityManager entityManager;
    
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Cart> findAll() {
        return getCurrentSession().createQuery("FROM Cart", Cart.class).list();
    }

    @Override
    public Cart findById(Long id) {
        return getCurrentSession().get(Cart.class, id);
    }

    @Override
    public void save(Cart cart) {
        entityManager.merge(cart);}

    @Override
    public void delete(Long id) {
    	 // 1️⃣ 從資料庫撈出 CartItem
        CartItem item = entityManager.find(CartItem.class, id);
        if (item != null) {
            // 2️⃣ 先從 Cart.items 集合移除，避免 cascade 衝突
            if (item.getCart() != null && item.getCart().getItems() != null) {
                item.getCart().getItems().remove(item);
            }
            // 3️⃣ 刪除資料庫中的 CartItem
            entityManager.remove(item);  // Hibernate 會同步刪除資料庫
        }  
    }
    @Override
    public void delete(Cart cart) {
        if (cart != null) {
            // 先清空 Cart.items 集合，避免 Hibernate cascade 衝突
            if (cart.getItems() != null) {
                cart.getItems().clear();
            }
            // 刪除 Cart
            entityManager.remove(entityManager.contains(cart) ? cart : entityManager.merge(cart));
        }
    }
    @Override
    public Cart findByCustomerId(Long customerId) {
    	String hql = "FROM Cart c WHERE c.customer.id = :customerId";
    	Cart cart = getCurrentSession().createQuery(hql ,Cart.class)
    	.setParameter("customerId", customerId)
    	.uniqueResult();
    	return cart;
    	
    }

}  


    
