package com.mbft.smbms.servlet.provider;

import com.mbft.smbms.pojo.Provider;
import com.mbft.smbms.service.provider.ProviderService;
import com.mbft.smbms.service.provider.ProviderServiceImpl;
import com.mbft.smbms.util.StringUtil01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            // providerList = providerService.getProviderListByParams(provider)

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









}
