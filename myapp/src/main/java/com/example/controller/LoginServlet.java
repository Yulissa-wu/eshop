package com.example.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.User;
import com.example.model.dao.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);
        

        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("view/welcome.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "帳號或密碼錯誤！");
            request.getRequestDispatcher("view/login.jsp").forward(request, response);
        }
        
    }
}