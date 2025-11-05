package com.example.demo.service;


import java.util.List;

import com.example.demo.model.User;

public interface LoginService {
	List<User> findAll();
	User login(String email, String password);
	User register(String email);
    void save(User login);
    User findByEmail(String email);
    

} 
