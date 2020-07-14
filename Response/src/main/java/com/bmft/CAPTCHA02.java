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
 * 在原来的基础上化干扰线
 * <p>
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
 * 6.使用画笔画出随机数字,在原来的基础上化干扰线
 * 7.使用ImageIO.write()方法吧图片传输到浏览器
 * <p>
 * 二、博客的步骤
 * 1. 设置刷新和缓存，（3种缓存方式防止浏览器不支持）
 * 2.new 内存图像 BufferedImage （区别于硬盘上的图像）
 */
public class CAPTCHA02 extends HttpServlet {
    private Random random = new Random();
    private int width = 80;
    private int height = 20;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 设置刷新和缓存，（3种缓存方式防止浏览器不支持）
        //设置不要缓存(3种方式，建议三种都设置，防止浏览器不支持)
        resp.addHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Expires", "0");
        //设置刷新时间 3秒
        resp.setHeader("refresh", "3");
        //2.new 内存图像 BufferedImage
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //3.创建画笔
        Graphics graphics = img.getGraphics();
        Random random = new Random();
        //4.使用画笔话背景
        graphics.setColor(new Color(0xF7FFFA));
        graphics.drawRect(0, 0, width, height);
        //5.使用画笔画出随机数字,,在原来的基础上化干扰线
        graphics.setColor(new Color(195, 18, 56));
        graphics.setFont(new Font(null, Font.BOLD, 20));
        int x = 0;
        for (int i = 0; i < 6; i++) {
            graphics.drawString(String.valueOf(random.nextInt(9)), x, 20);
            x += 20;
        }
        graphics.setColor(new Color(221, 174, 99));
        //6. 化随机坐标画干扰线，
        for (int i = 0; i < 5; i++) {
            graphics.drawLine(random.nextInt(width),random.nextInt(height),random.nextInt(width),random.nextInt(height));
        }
        //7.画完之后吧内存中画出的图片发送到客户端
        ImageIO.write(img,"jpeg",resp.getOutputStream());

    }

    //* 5.随机数字方法

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
