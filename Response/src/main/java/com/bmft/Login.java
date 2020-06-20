package com.bmft;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("成功收到请求");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //等于和equals的区别
        if (username.equals("root") && password.equals("123456")){
            resp.sendRedirect("loginSuccess.jsp?username="+username);
            System.out.println("登录成功:"+username);
        }
        else {
            req.setAttribute("message","账号或者密码错误，请重新输入");
            //通过请求转发（作用是返回到index.jsp页面.）
            //这里区分重定向，少了一个斜杠.???
            req.getRequestDispatcher("index.jsp").forward(req,resp);
            System.out.println("失败:"+username);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
