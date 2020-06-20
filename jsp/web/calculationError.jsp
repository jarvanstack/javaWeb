
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page errorPage="error/error500.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>这个页面有1/0的算术错误</h1>
<%System.out.println("=======进入calculationError.jsp=========");%>
<%System.out.println("=======这个页面有1/0的算术错误=========");%>
<%System.out.println(1/0);%>
</body>
</html>
