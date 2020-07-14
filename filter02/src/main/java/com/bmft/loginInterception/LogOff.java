package com.bmft.loginInterception;

import com.bmft.util.ConstantFilter02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 这个类在用户的登录成功进入主页的
 * 时候点击注销登录触发  注销操作，销毁对应的 USER_SESSION 的值
 * 然后跳转登录页面
 */
public class LogOff extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(ConstantFilter02.USER_SESSION,null);
        resp.sendRedirect("login.jsp");
}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
