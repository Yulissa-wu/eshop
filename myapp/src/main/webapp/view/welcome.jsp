<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>會員中心</title></head>
<body>
    <h2>歡迎 ${sessionScope.user.username}！</h2>
    <p>Email: ${sessionScope.user.username}</p>
    <a href="logout.jsp">登出</a>
</body>
</html>