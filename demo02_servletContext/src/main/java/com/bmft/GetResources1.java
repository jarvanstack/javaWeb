package com.bmft;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 获取resources下的资源文件
 */
public class GetResources1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("获取resources下的资源文件");
        InputStream inputStream = this.getServletContext().getResourceAsStream("WEB-INF/classes/db1.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String name = properties.getProperty("name");
        String age = properties.getProperty("age");
        resp.getWriter().write(name+" "+age);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
