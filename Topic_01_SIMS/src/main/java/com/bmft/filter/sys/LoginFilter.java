package com.bmft.filter.sys;

import javax.servlet.*;
import java.io.IOException;

/**
 * 实现对sys文件夹无Cookie并且无Session登录的拦截。
 */
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    public void destroy() {
        //
    }
}
