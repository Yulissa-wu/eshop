<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>使用者清單</title>
</head>
<body>
<h2>使用者清單</h2>

<table border="1" cellpadding="5">
<tr><th>ID</th><th>Name</th><th>age</th></tr>

<%
    String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String user = "wsq";
    String password = "004328qwer";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM test");

        while (rs.next()) {
            out.println("<tr>");
            out.println("<td>" + rs.getInt("id") + "</td>");
            out.println("<td>" + rs.getString("name") + "</td>");
            out.println("<td>" + rs.getString("age") + "</td>");
            out.println("</tr>");

        
        }rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        out.println("<tr><td colspan='3'>錯誤：" + e.getMessage() + "</td></tr>");
    }
%>

</table>
</body>
</html>