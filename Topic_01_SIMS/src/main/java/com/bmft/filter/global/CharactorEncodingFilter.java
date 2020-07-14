package com.bmft.filter.global;

import javax.servlet.*;
import java.io.IOException;

/**
 * 全局字符过滤防止，任何乱码问题
 *
 * 过滤器步骤复习
 * 1.实现接口 javax.servlet.Filter
 * 2.重写方法（记得过滤完毕后一定要释放，不然连接就卡在这里了）
 * （init()初始化执行一次，doFilter（）每次执行，destroy（）服务器结束执行一次)
 *
 */
public class CharactorEncodingFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        //init()初始化执行一次的代码
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //设置3个utf-8 实现字符过滤
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");
        //释放 request and response
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {
        //destroy（）服务器结束执行一次的代码
    }
}
