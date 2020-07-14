<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>请登录后再访问该页面！</h1>
<!--
1.  / （反斜杠）可以回到 localhost:8080/
2. 不加 / （反斜杠）就是相对于当前文件的目录
-->
<a href="${pageContext.request.contextPath}/login.jsp">返回登录页面</a>
</body>
</html>