<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<html>
<head><title>新增使用者</title></head>
<body>
<h2>新增使用者</h2>

<%
    String name = request.getParameter("name");
	String age = request.getParameter("age");

    if (name != null && age != null) {
        try {
            String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC";
            String user = "wsq";
            String password = "004328qwer";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = conn.prepareStatement("INSERT INTO test (name, age) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, age);
            ps.executeUpdate();

            ps.close();
            conn.close();
            out.println("<p> 已新增使用者：" + name + "</p>");
        } catch (Exception e) {
            out.println("<p> 錯誤：" + e.getMessage() + "</p>");
        }
    }
%>

<form method="post">
    姓名: <input type="text" name="name"><br>
    年齡: <input type="text" name="age"><br>
    <input type="submit" value="新增">
</form>
</body>
</html>