<%--

 3中基本语句块的学习

--%>

<%@ page import="java.util.Date" %>
<%@ page import="org.w3c.dom.ls.LSOutput" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>test01</title>
</head>
<body>
    <h1>一、基本2种语法的学习</h1>
    <%=new String("Hello")%>
    <%=new Date()%>
    <%--一.1.注释<%= out.println("")%>会编译失败--%>
    <h3></h3>
    <%
    out.println(" ");
    %>
    <hr>
    <%
      int sum = 0;
        for (int i = 0; i < 50; i++) {
            sum += i ;
        }
      out.println("2.jsp脚本片段:sum="+sum+"");
    %>
    <h1>二、特殊玩法</h1>
    <p>所有的变量都在一个方法中，叫做jspService(),<br>
    查看的路劲在....自己找，所有的jsp代码最终使用java代码来输出实现.<br>
    所以衍生出几种玩法.</p>
    <h2>玩法1.</h2>
    <%
        int x = 10;
        out.println("第一个jsp脚本代码块中可以访问x，x="+x);
    %>
    <hr>
    <%

        out.println("第2个jsp脚本代码块中可以访问x，x="+x);
    %>
    <h2>玩法2.遍历输出中间的标签</h2>
    <%
        for (int i = 0; i < 5; i++) {
    %>
    <h5>被遍历的h5标签</h5>
    <%
        }
    %>
    <%!
        private String name;
        private int age;
        static {
            //这里不能写out.println()是因为out 是一个
            //在_jspService（）方法里声明的常量。
            System.out.println("hello");
        }
    %>
</body>
</html>
