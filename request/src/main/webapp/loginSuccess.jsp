<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>loginSuccess</title>
</head>
<body>
<h1>Login Success！登录成功！</h1>
<h2>
    <%
        out.print("用户:"+request.getParameter("username"));
    %>
</h2>

</body>
</html>
