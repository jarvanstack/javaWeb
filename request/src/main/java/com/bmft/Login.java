package com.bmft;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Login
 * 复习一遍之前response写的登录效果
 * 添加复选框内容
 * 一、实现效果
 * 1.登录界面，识别成功使用response重定向到登录成功页面,并打印用户名称.
 * 2.识别失败使用request转发到原页面，并打印错误信息
 */
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] hobbies = req.getParameterValues("hobbies");
        System.out.println("============");
        System.out.println(username);
        System.out.println(password);
        System.out.println(Arrays.toString(hobbies));
        System.out.println("============");
        // * 1.登录界面，
        // 识别成功使用response重定向到登录成功页面,并打印用户名称.
        if (username.equals("root") && password.equals("123456")){
            System.out.println("登录成功!");
            resp.sendRedirect("loginSuccess.jsp?username="+username);
        }
        // * 2.识别失败使用request转发到原页面，并打印错误信息
        else {
            System.out.println("登录失败");
            req.setAttribute("message","账号或者密码错误请重新登录");
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }
   }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
