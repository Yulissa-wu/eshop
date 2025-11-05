<%
    Cookie userCookie = new Cookie("username", "Tom");
    userCookie.setMaxAge(60*60*24); // 1 天
    response.addCookie(userCookie);
%>