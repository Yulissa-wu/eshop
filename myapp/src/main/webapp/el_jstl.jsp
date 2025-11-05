<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head><title>JSTL + EL 範例</title></head>
<body>
<%
   java.util.List<String> users = new java.util.ArrayList<>();
   users.add("Alice");
   users.add("Bob");
   users.add("Charlie");
   request.setAttribute("userList", users);
%>
<h3>使用 JSTL + EL 輸出清單</h3>
<ul>
   <c:forEach var="u" items="${userList}">
       <li>${u}</li>
   </c:forEach>
</ul>
<c:if test="${fn:length(userList) > 2}">
   <p>使用者數量超過 2</p>
</c:if>
</body>
</html>