<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="user" class="com.example.User" scope="request" />
<jsp:setProperty name="user" property="*" />

<html>
<body>
<h1>使用者資料</h1>
<p>姓名：<jsp:getProperty name="user" property="name" /></p>
<p>年齡：<jsp:getProperty name="user" property="age" /></p>
</body>
</html>