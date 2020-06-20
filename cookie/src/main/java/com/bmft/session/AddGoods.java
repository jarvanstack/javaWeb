package com.bmft.session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 用户点击商品后，实现的添加至购物车的逻辑和
 * 返回是否继续页面的类
 */

public class AddGoods extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");//设置客户端的接受方式
        System.out.println("=======AddGoods进入成功=========");

        if (req.getSession().getAttribute("sessionGoods") == null) {
            req.getSession().setAttribute("sessionGoods", new HashMap<String, Integer>());
        }
        HashMap<String, Integer> map = (HashMap<String, Integer>) req.getSession().getAttribute("sessionGoods");

        //1.获取提交的表单,如何获得多个数据通过一个关键字呢
        //req.getParameterValues
        String[] goods = req.getParameterValues("goods");
        PrintWriter out = resp.getWriter();

        //如果购物的提交的post goods不为空就，显示添加成功，并返回是否继续的
        //或者结算的页面
        if (goods != null) {
            for (String good : goods) {
                if (map.get(good) == null) {
                    map.put(good, 0);
                }
                map.put(good, map.get(good) + 1);
                out.write("<h3>添加" + good + "成功</h3><br>");
                System.out.println("<h3>添加" + good + "成功</h3><br>");
            }

        } else {
            out.write("<h3>您没有选择任何商品哦</h3>");
            System.out.println("<h3>您没有选择任何商品哦</h3>");
        }
        out.write("<a href='./ShowGoods'><h1>继续购买</h1></a>");
        out.write("<a href='./Cart'><h1>购物车</h1></a>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
