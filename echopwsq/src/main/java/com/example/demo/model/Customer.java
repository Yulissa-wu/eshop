package com.example.demo.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Cart cart;
    
    // Constructors
    public Customer() {}

//    public Costomers(String cust_name, int cust_num) {
//        this.cust_name = cust_name;
//        this.cust_num = cust_num;
//    }

    // Getters and Setters




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	@Override
    public String toString() {
        return "Customers{" +
                "id" + id +
                '}';
    }

	public void setUser_id(String valueOf) {
		// TODO Auto-generated method stub
		
	}

}
