<%--
这个jsp就是导入公共页面的实例，使用<jsp:include
推荐使用.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<jsp:include page="..//comment/hearder.jsp"> </jsp:include>
<body>
<h1>body身体</h1>
</body>
<jsp:include page="..//comment/footer.jsp"> </jsp:include>
</html>
