<%--
一、<%@ 的3种方法
2.include 包含页面（一般用于设置公共的头或者尾部）

这个jsp就是导入公共页面的实例，使用<%@include
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%@include file="../comment/hearder.jsp"%>
<body>
<h1>这是body体</h1>
</body>
<%@include file="../comment/footer.jsp"%>
</html>
