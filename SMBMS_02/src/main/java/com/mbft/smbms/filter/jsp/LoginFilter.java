package com.mbft.smbms.filter.jsp;

import com.mbft.smbms.util.Constants01;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jsp文件下登录过滤
 * （转发需要使用HttpServletRequest但是，放行请使用原来的servletRequest）
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化代码
    }

    /**
     * 实现对jsp文件下无 session 的过滤（记得放行）
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //过滤开始
        //需要用到HttpServletRequest，需要自己强转。(放行的时候请使用原来的）
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //如果没有session就给他跳转到登录界面,使用转发降低浏览器的压力。

        if (request.getSession().getAttribute(Constants01.USER_SERSSION)==null){
            //加上反斜杠就是 http://localhost:8080/SMBMS_02_war/ 这个目录，
            // 【区分与 html ，jsp里面的链接。】
            request.getRequestDispatcher("/error.jsp").forward(servletRequest,servletResponse);
        }
        //记得放行。
        filterChain.doFilter(servletRequest,servletResponse);
        //过滤结束(写完一个Filter或者Servlet就web.xml去注册）
    }

    @Override
    public void destroy() {
        //销毁执行的代码
    }
}
