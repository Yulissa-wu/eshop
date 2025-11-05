<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>會員登入</title></head>
<body>
    <h2>會員登入</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        帳號：<input type="text" name="username"><br>
        密碼：<input type="password" name="password"><br>
        <input type="submit" value="登入">
    </form>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
</body>
</html>