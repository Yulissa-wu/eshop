<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>登入結果</title></head>
<body>
<%
    String user = request.getParameter("username");
    String pass = request.getParameter("password");

    if ("admin".equals(user) && "1234".equals(pass)) {
        out.println("<h3> 歡迎，" + user + "</h3>");
    } else {
        out.println("<h3> 帳號或密碼錯誤</h3>");
    }
%>
</body>
</html>