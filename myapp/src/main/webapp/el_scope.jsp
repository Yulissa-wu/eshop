<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>EL 範圍變數</title></head>
<body>
<%
    pageContext.setAttribute("msg", "Page 範圍");
    request.setAttribute("msg", "Request 範圍");
    session.setAttribute("msg", "Session 範圍");
    application.setAttribute("msg", "Application 範圍");
%>

<p>不指定範圍（會由小到大找）：${msg}</p>
<p>PageScope：${pageScope.msg}</p>
<p>RequestScope：${requestScope.msg}</p>
<p>SessionScope：${sessionScope.msg}</p>
<p>ApplicationScope：${applicationScope.msg}</p>
</body>
</html>