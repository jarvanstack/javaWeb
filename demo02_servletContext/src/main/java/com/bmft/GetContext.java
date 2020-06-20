package com.bmft;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 这个类用于获得提交的Context里面的内容
 */
public class GetContext extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GetContext成功");
        //这个类用于获得提交的Context里面的内容
        ServletContext servletContext = this.getServletContext();
        Object name = servletContext.getAttribute("name");
        //2.返回页面给浏览器
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<h1>提取名称："+(String)name+"</h1>");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
