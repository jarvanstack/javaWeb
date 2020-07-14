package com.mbft.smbms.servlet.user;

import com.mbft.smbms.pojo.User;
import com.mbft.smbms.service.user.UserServiceImpl;
import com.mbft.smbms.util.Constants01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoginServlet 实现用户点击登录的访问跳转
 * 1.接受用户的post的账号密码
 * 2.service业务逻辑返回user
 * 3.如果user不为空就储存session，并且重定向到主页面
 * 4.如果user为空就[转发]原界面并返回错误信息。
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.接受用户的post的账号密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //2.
        User user = new UserServiceImpl().login(userCode,userPassword);
        //3.
        if (user != null) {
            req.getSession().setAttribute(Constants01.USER_SERSSION,user);
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
