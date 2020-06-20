package com.bmft;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 这个类用于实现文件的下载
 * //1.获取下载路径和 获取下载文件名称（重点）
 * //2.设置头为下载文件头（重点）
 * //3.使用之前的IO缓冲输出文件
 */
public class DownLoadFile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取下载路径和获取下载文件名称\
        String absolutePath = "C:\\Users\\25301\\Pictures\\壁纸\\高清后\\coffeeGirl.jpg";
        //一种自动获取文件名称的方式，也可用分割实现
        String fileName = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1);
        //2.设置头为下载文件头（重点）
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("图片"+fileName, "UTF-8"));
        //3.使用之前的IO缓冲输出文件
        FileInputStream fileInputStream = new FileInputStream(absolutePath);
        int length = 0;
        byte[] bytes = new byte[1024 * 10];
        ServletOutputStream outputStream = resp.getOutputStream();
        while ((length = fileInputStream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, length);
        }
        fileInputStream.close();
        outputStream.close();
        System.out.println("图片传输成功");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
