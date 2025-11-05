<%@ page contentType="text/html; charset=UTF-8" %>
<%! 
    // 成員變數
    int counter = 0;

    // 成員方法
    public String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
%>

<html>
<body>
<h1><%= getGreeting("Terry") %></h1>
<p>訪問次數：<%= ++counter %></p>
</body>
</html>