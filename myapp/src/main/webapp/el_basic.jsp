<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>EL 基本範例</title></head>
<body>
<%
    request.setAttribute("username", "Alice");
    request.setAttribute("age", 25);
%>

<p>傳統 JSP 表達式：<%= request.getAttribute("username") %></p>
<p>EL 表達式：${username}</p>
<p>EL 運算：${age + 5}</p>
</body>
</html>