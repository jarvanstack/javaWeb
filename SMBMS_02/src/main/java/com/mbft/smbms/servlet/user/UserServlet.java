package com.mbft.smbms.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mbft.smbms.pojo.User;
import com.mbft.smbms.service.user.UserServiceImpl;
import com.mbft.smbms.util.Constants01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 这个类实现对用户User的操作。
 * 方法提出来，可以实现复用，和（一个请求jsp/user.do 根据不同的
 * 【method参数值】调用不同的提出来的方法，）
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        //如果传入method的参数是，savepwd就执行的方法，修改密码
        if (method != null) {
            if (method.equals("savepwd")){
                savepwd(req,resp);
            }
            //如果 pwdmodify 执行前端的旧密码验证
            else if (method.equals("pwdmodify")) {
               pwdmodify(req,resp);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 修改密码：如果传入method的参数是，savepwd就执行的方法，修改密码
     * 1.接受前端的旧密码并判断和User里面的密码是否匹配，返回对应的页面和message或者继续。
     * 2.旧密码正确的情况下，
     * @param req
     * @param resp
     */
    private void savepwd(HttpServletRequest req, HttpServletResponse resp){
        Object user = req.getSession().getAttribute(Constants01.USER_SERSSION);
        //先判断user是否为空.
        if (user != null) {
            //1.接受前端的旧密码并判断和User里面的密码是否匹配，返回对应的页面和message或者继续。
            String oldpassword = req.getParameter("oldpassword");
            user = (User)user;
            if (((User) user).getUserPassword().equals(oldpassword)){
                //继续【调用service层】，修改，提示message,消除session
                boolean flag = new UserServiceImpl().updatePassword(((User) user).getId(), req.getParameter("newpassword"));
                if (flag){
                    try {
                        //先修改成功在转发，不然怎么显示？？？
                        req.setAttribute(Constants01.MESSAGE,"修改成功，请重新登录！");
                        req.getSession().setAttribute(Constants01.USER_SERSSION,null);
                        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    //转发对应的页面和message
                    req.setAttribute(Constants01.MESSAGE,"错误");
                    try {
                        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }else {
                //转发对应的页面和message
                req.setAttribute(Constants01.MESSAGE,"旧密码输入不正确!请重新输入");
                try {
                    req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 前端旧密码ajax验证
     * 阿里巴巴【JSONArray静态类】
     * @param req
     * @param resp
     */
    private void pwdmodify(HttpServletRequest req, HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constants01.USER_SERSSION);
        HashMap<String,String> map = new HashMap<String, String>();
        //session 过期
        if (o==null){
            map.put(Constants01.RESULT,"sessionerror");
        }
        //旧密码为空
        else if (req.getParameter("oldpassword")==null){
            map.put(Constants01.RESULT,"error");
        }
        else {
            User user = (User)o;
            //旧密码不正确
            if (!req.getParameter("oldpassword").equals(user.getUserPassword())) {
                map.put(Constants01.RESULT,"false");
            }else{
                map.put(Constants01.RESULT,"true");
            }

        }
        //返回数据
        resp.setContentType("application/json");
        PrintWriter out = null;
        try {
            System.out.println(map);
             out = resp.getWriter();
            out.write(JSONArray.toJSONString(map));
            out.flush();//刷新

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源操作
            assert out != null;
            out.close();//关闭资源
        }

    }
}
