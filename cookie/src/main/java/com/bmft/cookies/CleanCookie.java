package com.bmft.cookies;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 删除Cookie效果实现.
 * 将Cookie有效期设置为0，就立马过期。
 */
public class CleanCookie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie lastLoginTime = new Cookie("lastLoginTime", System.currentTimeMillis() + "");
        lastLoginTime.setMaxAge(0);
        resp.addCookie(lastLoginTime);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
