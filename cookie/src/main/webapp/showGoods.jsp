<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品展示Show Goods</title>
</head>
<body>
<h1>商品展示Show Goods</h1>
<form action="<%=request.getContextPath()%>/AddGoods" method="get">
    《Java编程思象》<input type="checkbox" name="goods" value="《Java编程思象》"><br>
    《JavaWeb入门到放弃》<input type="checkbox" name="goods" value="《JavaWeb入门到放弃》"><br>
    《Java入土实践》<input type="checkbox" name="goods" value="《Java入土实践》"><br>
    <input type="submit" value="加入购物车">
</form>
</body>
</html>
