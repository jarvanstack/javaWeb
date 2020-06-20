package com.bmft.cookies;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 实现上次登录的时间Cookie的保存和发送，
 *
 * 如果有Cookie就打印上次登录的时间，并返回这次CurrentTime作为这次登录的时间，
 * 如果没有Cookie就打印这是你第一次登录
 * Cookie默认存活的时间是 10秒钟，
 */
public class CookieLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");//设置客户端的接受方式

        javax.servlet.http.Cookie[] cookies = req.getCookies();
        PrintWriter out = resp.getWriter();
        boolean isFirstTime = true;
        if (cookies != null){
            for (int i = 0; i < cookies.length; i++) {
                //如果有Cookie就打印上次登录的时间，并返回这次CurrentTime作为这次登录的时间，
               if (cookies[i].getName().equals("lastLoginTime")){
                   out.write("上次登录的时间:"+cookies[i].getValue());
                   isFirstTime = false;
               }
            }
        }
        if (isFirstTime){
            out.write("这是您的第一次登录");
        }
        Cookie cookie = new Cookie("lastLoginTime",System.currentTimeMillis()+"");
        cookie.setMaxAge(10);
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
