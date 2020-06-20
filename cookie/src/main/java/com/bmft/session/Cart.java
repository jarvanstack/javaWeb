package com.bmft.session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 购物车展示类
 * 更具用户session，展示对应是商品
 */
public class Cart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");//设置客户端的接受方式
        //清除商品
        if (req.getParameter("isInvalidate") != null && req.getParameter("isInvalidate").equals("true")) {

            req.getSession().setAttribute("sessionGoods", null);
        }
        PrintWriter out = resp.getWriter();
        if (req.getSession().getAttribute("sessionGoods") != null) {
            HashMap<String, Integer> map = (HashMap<String, Integer>) req.getSession().getAttribute("sessionGoods");
            //展示对应是商品
            for (String s : map.keySet()) {
                out.write("<h4>" + s + " 数量 " + map.get(s) + "</h4>");
            }

        } else {
            out.write("<h4>" + "您的购物车空荡荡的哦" + "</h4>");
        }
        out.write("<a href='./ShowGoods'><h1>继续购买</h1></a>");
        out.write("<a href='./Cart?isInvalidate=true'><h1>清除商品</h1></a>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
