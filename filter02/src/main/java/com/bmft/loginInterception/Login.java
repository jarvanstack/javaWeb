package com.bmft.loginInterception;


import com.bmft.util.ConstantFilter02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bmft.util.ConstantFilter02.*;

/**
 * 这个类是用户在登录界面
 * 点击登录后实现跳转逻辑的Servlet
 */
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || password == null){
            System.out.println("====登录失败null====");
            resp.sendRedirect("loginFailed.jsp");
        }
        else if (username.equals("root") && password.equals("123456")) {
            req.getSession().setAttribute(USER_SESSION, req.getSession().getId());
            System.out.println("====登录成功====");
            resp.sendRedirect("sys/homePage.jsp");
        } else {
            System.out.println("====登录失败====");
            resp.sendRedirect("loginFailed.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
