package com.mbft.smbms.filter.global;
import javax.servlet.*;
import java.io.IOException;

public class CharacterFilter02 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化代码
        System.out.println("-- init() test");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
