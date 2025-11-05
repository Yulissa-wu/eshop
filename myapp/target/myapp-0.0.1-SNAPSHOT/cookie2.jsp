<%@ page contentType="text/html; charset=UTF-8" %>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("username".equals(c.getName())) {
                out.print("歡迎回來，" + c.getValue());
            }
        }
    }
%>