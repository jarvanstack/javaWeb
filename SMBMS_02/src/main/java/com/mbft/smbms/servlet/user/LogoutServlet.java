package com.mbft.smbms.servlet.user;

import com.mbft.smbms.util.Constants01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现对退出点击的操作/jsp/logout.do
 * 1.清理session，
 * 2.重定向到login.jsp
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.
        Object attribute = req.getSession().getAttribute(Constants01.USER_SERSSION);
        if (attribute!=null){
            req.getSession().setAttribute(Constants01.USER_SERSSION,null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
