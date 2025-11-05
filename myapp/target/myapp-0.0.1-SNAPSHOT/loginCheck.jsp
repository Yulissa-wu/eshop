<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jakarta.servlet.http.*,jakarta.servlet.*" %>
<%
    String user = request.getParameter("username");
    String pass = request.getParameter("password");

    if ("admin".equals(user) && "1234".equals(pass)) {
        // 建立 Session
        session.setAttribute("username", user);
        response.sendRedirect("welcome.jsp");
    } else {
        out.print("帳號或密碼錯誤！<a href='login2.jsp'>重新登入</a>");
    }
%>