package com.bmft;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * CAPTCHA是验证码是意思
 * 这个类用于响应一张验证码图片
 * Completely Automated Public Turing test to tell Computers and Humans Apart
 * <p>
 * 一、步骤
 * 1.为了效果展示让浏览器3秒刷新一次，设置图片打开并禁止浏览器缓存
 * 2.内存中构建一个图片BufferedImage图片类了解
 * 3.用图片的img.getGraphics()方法获得图片画笔
 * 4.使用画笔化背景
 * 5.随机数字方法
 * 6.使用画笔画出随机数字
 * 7.使用ImageIO.write()方法吧图片传输到浏览器
 */
public class CAPTCHA extends HttpServlet {
    private Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getContextPath());
        //* 1.为了效果展示让浏览器3秒刷新一次，设置图片打开并禁止浏览器缓存
        resp.setHeader("refresh", "3");
        resp.setContentType("image/jpeg");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        //* 2.内存中构建一个图片BufferedImage图片类了解
        BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
        //* 3.用图片的img.getGraphics()方法获得图片画笔
        Graphics graphics = image.getGraphics();
        //* 4.使用画笔化背景
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, 80, 20);
        //* 6.使用画笔画出随机数字
        graphics.setColor(new Color(193, 28, 34));
        graphics.setFont(new Font(null, Font.BOLD, 20));//设置字体
        graphics.drawString(getRandomNumber(), 0, 20);
        //* 7.使用ImageIO.write()方法吧图片传输到浏览器
        ImageIO.write(image, "jpeg", resp.getOutputStream());
    }

    //* 5.随机数字方法
    private String getRandomNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(random.nextInt(9999999));
        for (int i = 0; i < 7 - stringBuilder.length(); i++) {
            stringBuilder.append("0");//保证有7位数字不够0来填充
        }
        System.out.println("随机数为: " + stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
