<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>註冊結果</title></head>
<body>
<%
    String email = request.getParameter("email");
    String pwd1 = request.getParameter("password");
    String pwd2 = request.getParameter("confirm");

    if (email == null || email.trim().isEmpty() ||
        pwd1 == null || pwd1.trim().isEmpty()) {
        out.println("<p> Email 與密碼不可為空</p>");
    } else if (!pwd1.equals(pwd2)) {
        out.println("<p> 兩次密碼不一致</p>");
    } else {
        out.println("<p> 註冊成功！Email: " + email + "</p>");
    }
%>
</body>
</html>