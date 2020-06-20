<!-- 解决中文乱码问题 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<h1>登录界面</h1>
<!-- war里面就是打包的项目所有文件，所以先找打war包的地址然后再访问映射 -->
<form action="<%=request.getContextPath()%>/Login" method="post">
    账号<input type="text" name="username"><br>
    密码<input type="password" name="password"><br>
    <input type="submit" value="提交">
</form>
<h2>
    <%
        if (request.getAttribute("message")!=null){
            out.print(request.getAttribute("message"));
        }
    %>
</h2>
</html>