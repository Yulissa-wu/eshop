package com.example.demo.service.impl;

import com.example.demo.dao.CartDAO;
import com.example.demo.dao.CartItemDAO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import javax.persistence.TypedQuery;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDAO cartRepository;
    
    @Autowired
    private CartItemDAO cartItemRepository;
    
        
    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public void saveCart(Cart cart) {
    	cartRepository.save(cart);
    }

    @Override
    public void updateCart(Long id, Cart updatedCart) {
    	Cart existingCart = cartRepository.findById(id);
        if (existingCart != null) {
//            existingCart.setCart_name(updatedCart.getCart_name());
//            existingCart.setCart_price(updatedCart.getCart_price());
            cartRepository.save(existingCart);
        }
    }

    @Override
    @Transactional
    public void deleteCart(Long id) {
        // 1️⃣ 從資料庫撈出 Cart
        Cart cart = cartRepository.findById(id);
//        if (cart != null) {
            // 2️⃣ 先刪除 Cart 底下所有 CartItem，避免 cascade 衝突
//            if (cart.getItems() != null) {
//                for (CartItem item : cart.getItems()) {
//                	cartRepository.delete(item.getId()); // 逐一刪除 CartItem
//                }
//                cart.getItems().clear();
//            }

            // 3️⃣ 刪除 Cart
            cartRepository.delete(cart); // DAO 內用 EntityManager 或 Hibernate 刪除資料庫
        }
    



    @Override
    public void addProductToCart(Customer customer, Product product, int quantity) {
        Cart cart = getByCustomerId(customer);

        if (cart.getItems() == null) {
            cart.setItems(new HashSet<>());
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem); // ✅ 寫入資料庫
        }

        cartRepository.save(cart);
    }

    private Cart getByCustomerId(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	private Cart getCartByCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public List<CartItem> getAllCartItemsByUser(Long userId) {
        // 透過 DAO 去查該使用者的購物車項目
        return cartItemRepository.findAllByUserId(userId);
    }


	
    @Override
    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public void saveCartItem(Long cartId, CartItem cartItem) {
        Cart cart = cartRepository.findById(cartId); // 這裡回傳 Cart

        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }

        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
    }



	@Override
	public Cart findByCustomerId(Long id) {
		return cartRepository.findByCustomerId(id);
		
	}

	@Override
	public void saveCart(CartItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CartItem getCartItemById(Long cartItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCart(Cart cart) {
		// TODO Auto-generated method stub
		
	}







}
