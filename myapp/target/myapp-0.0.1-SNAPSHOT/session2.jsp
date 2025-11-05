<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String username = (String) session.getAttribute("username");
    if (username != null) {
        out.print("目前使用者：" + username);
    } else {
        out.print("尚未登入！");
    }
%>