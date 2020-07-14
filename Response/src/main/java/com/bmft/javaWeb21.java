package com.bmft;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Random;

public class javaWeb21 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.1以下代码会查找本机的编码进行发送（GBK）
        String date1 = "你好世界";
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(date1.getBytes());
        //1.2.使用writer返回字符
        resp.getWriter().write("你好世界");
        //2.1 返回下载文件
        //(1)设置下载文件头（最关键的一步）
        resp.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("妹子图"+new Random().nextInt(10000), "UTF-8"));
        //（2）使用IO知识完成文件返回相应
        //(2.1)准备输入流，读取图片
        String absolutePath = "C:\\Users\\25301\\Pictures\\壁纸\\高清后coffeeGirl.jpg";
        int length = 0;
        byte[] bytes = new byte[1024 * 100];
        FileInputStream inputStream = new FileInputStream(absolutePath);
        //（2.2)准备相应输出流，给客户相应图片
        ServletOutputStream outputStream1 = resp.getOutputStream();
        //(2.3)读取图片并相应回去
        while ((length =inputStream.read(bytes))!=-1){
            outputStream1.write(bytes,0,length);
        }
        inputStream.close();
        outputStream.close();
        System.out.println("图片传输成功");



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
