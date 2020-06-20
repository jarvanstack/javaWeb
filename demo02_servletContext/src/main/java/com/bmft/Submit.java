package com.bmft;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个歌Servlet用于提交name到ServletContext，
 *
 */
public class Submit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这里没有表单提交先凑合字节写一个测试.
        String name = "张三";
        ServletContext servletContext = this.getServletContext();
        servletContext.setAttribute("name",name);
        //返回提交成功的页面
        resp.setContentType("text/html;charset=UTF-8");//乱码问题解决
        resp.getWriter().write("<h1>提交成功!张三</h1>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
