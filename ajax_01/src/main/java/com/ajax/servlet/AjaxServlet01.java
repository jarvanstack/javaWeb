package com.ajax.servlet;

import com.alibaba.fastjson.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;

public class AjaxServlet01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println("method====== "+method);
        if (method.equals("login")) {
            //
            login(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        String username = req.getParameter("username");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("进入login");
        boolean flag = false;
        if (username.equals("123456")){
            flag = true;
        }
        HashMap<String, String> data = new HashMap<>();

        data.put("message",""+flag);
        Writer out = null;
        resp.setContentType("application/json");
        try {
            out = resp.getWriter();
            out.write(JSONArray.toJSONString(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}