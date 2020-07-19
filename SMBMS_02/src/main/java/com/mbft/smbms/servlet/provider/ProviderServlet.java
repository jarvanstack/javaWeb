package com.mbft.smbms.servlet.provider;

import com.alibaba.fastjson.JSONArray;
import com.mbft.smbms.pojo.Provider;
import com.mbft.smbms.pojo.User;
import com.mbft.smbms.service.provider.ProviderService;
import com.mbft.smbms.service.provider.ProviderServiceImpl;
import com.mbft.smbms.util.Constants01;
import com.mbft.smbms.util.StringUtil01;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProviderServlet extends HttpServlet {
    ProviderService providerService = new ProviderServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            if (method.equals("query")) {
                //provider list
                query(req,resp);
            }else if (method.equals("view")){
                view(req,resp);
            }else if (method.equals("modify")){
                modify(req,resp);
            }else if (method.equals("modifysave")){
                modifysave(req,resp);
            }else if (method.equals("delprovider")){
                delprovider(req,resp);
            }else if (method.equals("add")){
                add(req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 展示 provider 列表 list 的方法
     * @param req
     * @param resp
     */
    private void query(HttpServletRequest req, HttpServletResponse resp){
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");
        List<Provider> providerList = null;
        // 使用list 封装 方便以后拓展
        if (StringUtil01.isNullOrEmpty(queryProCode)&& StringUtil01.isNullOrEmpty(queryProName)){
            //如果参数都为空，就可以走全查询通道
            providerList = providerService.getProviderList();
        }else {
            //如果有参数就走条件动态查询通道
            Provider provider = new Provider();
            provider.setProCode(queryProCode);
            provider.setProName(queryProName);

            providerList = providerService.getProviderListByParams(provider);

        }

        req.setAttribute("queryProCode",queryProCode);
        req.setAttribute("queryProName",queryProName);
        req.setAttribute("providerList",providerList);
        try {
            req.getRequestDispatcher("providerlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击查看provider信息执行的方法，
     * req. provider对象
     * 和转发
     * @param req
     * @param resp
     */
    private void view(HttpServletRequest req, HttpServletResponse resp) {
        String proid = req.getParameter("proid");
        Provider provider = providerService.getProviderById(proid);
        req.setAttribute("provider",provider);
        try {
            req.getRequestDispatcher("providerview.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 进入修改页面
     * @param req
     * @param resp
     */
    private void modify(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("proid");
        Provider provider = providerService.getProviderById(id);

        System.out.println("-->"+provider.getProName());
        req.setAttribute("provider",provider);
        try {
            req.getRequestDispatcher("providermodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 提交修改表单走的方法
     * @param req
     * @param resp
     */
    private void modifysave(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        String id = req.getParameter("id");
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");//8

        Provider provider = providerService.getProviderById(id);

        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);//7/ 除了id，id是自带的

        //设置修改人和修改时间
        Object attribute = req.getSession().getAttribute(Constants01.USER_SERSSION);
        if (attribute != null) {
            User user = (User) attribute;
            provider.setModifyBy(user.getId());
        }
        provider.setModifyDate(new Date());//10 加上 id

         flag = providerService.modifyProvider(provider);


        if (flag) {
            //如果修改成功
            try {
                req.getRequestDispatcher("provider.do?method=query").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //如果修改失败
            req.setAttribute("error","error");
            req.setAttribute("provider",provider);
            try {
                req.getRequestDispatcher("providermodify.jsp").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击删除按钮的删除 方法
     * @param req
     * @param resp
     */
    private void delprovider(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        String id = req.getParameter("uid");//删除用户的id
        HashMap<String, String> data = new HashMap<>();//前端需要json，就给json，
        //别乱写，前端需要什么，就给什么，前端需要json，就给json，前端需要json，就给json，别乱写，
        //前端需要json，就给json，别乱写

        //执行service的删除 flag = userService.deleteUser(id);

        if (providerService.getProviderById(id) == null) {
            //如果用户不存在
            data.put("delResult", "notexist");
        } else {//如果用户存在执行删除
            flag = providerService.deleteProviderById(id);
            if (flag) {
                //如果删除成功f
                data.put("delResult", "true");
            } else {//如果删除失败
                data.put("delResult", "false");
            }


        }
        //返回对应的 数据
        resp.setContentType("application/json");
        ServletOutputStream out = null;
        try {
            out = resp.getOutputStream();
            out.write(JSONArray.toJSONBytes(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 添加提交表单执行的方法
     * @param req
     * @param resp
     */
    private void add(HttpServletRequest req, HttpServletResponse resp){
        boolean flag = false;
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc"); // 7 不加 id
        Provider provider = new Provider();//新建provider 区别于modify

        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);//7/ 除了id，id是自带的

        //设置修改人和修改时间
        Object attribute = req.getSession().getAttribute(Constants01.USER_SERSSION);
        if (attribute != null) {
            User user = (User) attribute;
            provider.setCreatedBy(user.getId());
        }
        provider.setCreationDate(new Date());//10 加上 id

        flag = providerService.addProvider(provider);


        if (flag) {
            //如果修改成功
            try {
                req.getRequestDispatcher("provider.do?method=query").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //如果修改失败
            req.setAttribute("error","error");
            req.setAttribute("provider",provider);
            try {
                req.getRequestDispatcher("provideradd.js.jsp").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }





}
