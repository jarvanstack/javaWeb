package com.mbft.smbms.servlet.bill;

import com.alibaba.fastjson.JSONArray;
import com.mbft.smbms.pojo.Bill;
import com.mbft.smbms.pojo.Provider;
import com.mbft.smbms.pojo.User;
import com.mbft.smbms.service.bill.BillService;
import com.mbft.smbms.service.bill.BillServiceImpl;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * jsp/bill.do 请求执行的Servlet
 */
public class BillServlet extends HttpServlet {
    BillService billService = new BillServiceImpl();
    ProviderService providerService = new ProviderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (StringUtil01.isNotNull(method)) {//方法不能为空
            if (method.equals("query")) {//query
                query(req, resp);
            } else if (method.equals("getproviderlist")) {//获取 provider list
                getProviderList(req, resp);//在添加的时候这个方法已经写好了
            } else if (method.equals("add")) {
                //订单添加
                add(req,resp);
            }else if (method.equals("view")){
                view(req,resp);//订单浏览
            }else if (method.equals("modify")){
                //点击修改弹出 修改界面
                modify(req,resp);
            }else if (method.equals("modifysave")){
                modifysave(req,resp);
            }else if (method.equals("delbill")){
                //点击删除按钮除触发的方法
                delbill(req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * bill list 界面方法
     *
     * @param req
     * @param resp
     */
    private void query(HttpServletRequest req, HttpServletResponse resp) {
        String queryProductName = "";// 前端参数
        List<Provider> providerList = null;// --- 数据库
        String queryProviderId = "";//提供商id 前端的选择
        String queryIsPayment = "";// 是否支付的id 前端的选择
        List<Bill> billList = null;// --- 数据库 5
        Bill bill = new Bill();
        //数据准备
        queryProductName = req.getParameter("queryProductName");
        queryProviderId = req.getParameter("queryProviderId");
        queryIsPayment = req.getParameter("queryIsPayment");
        bill.setProductName(queryProductName);
        if (StringUtil01.isNotNull(queryIsPayment)) {
            bill.setProviderId(Integer.parseInt(queryProviderId));
        }
        if (StringUtil01.isNotNull(queryIsPayment)) {
            bill.setIsPayment(Integer.parseInt(queryIsPayment));
        }
        providerList = providerService.getProviderList();
        billList = billService.getBillList(bill);


        //返回数据
        req.setAttribute("queryProductName", queryProductName);
        req.setAttribute("queryProviderId", queryProviderId);
        req.setAttribute("queryIsPayment", queryIsPayment);
        req.setAttribute("billList", billList);
        req.setAttribute("providerList", providerList);
        try {
            //
            req.getRequestDispatcher("billlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * billadd.jsp ajax获取 provider方法
     *
     * @param req
     * @param resp
     */
    private void getProviderList(HttpServletRequest req, HttpServletResponse resp) {
        //返回json 直接 data ?
        List<Provider> data = null;
        ServletOutputStream out = null;
        // data = providerService.getProviderList();
        data = providerService.getProviderList();
        try {
            resp.setContentType("application/json");
            out = resp.getOutputStream();
            out.write(JSONArray.toJSONBytes(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean flag = false;
        Bill bill = new Bill();
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");//7
        String productDesc = req.getParameter("productDesc");// 这个是以后拓展用的 ，那个表没有描述


        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount));
        bill.setTotalPrice(new BigDecimal(totalPrice));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setProductDesc(productDesc);//拓展用
        //额外添加
        bill.setCreationDate(new Date());//添加时间
        User modifyUser = (User) req.getSession().getAttribute(Constants01.USER_SERSSION);
        bill.setCreatedBy(modifyUser.getId());//添加 人 id  9


        // 执行添加操作
        flag = billService.addBill(bill);


        if (flag) {//如果添加成功,重定向到userlist.jsp 界面
            System.out.println("--->添加成功");
            req.setAttribute("success", "添加成功");
            resp.sendRedirect("bill.do?method=query");//使用这个方法实现了页面的刷新
        } else {//如果添加失败 转发到当前页面
            System.out.println("--->添加失败");
            req.setAttribute("error", "添加失败");
            //
            req.getRequestDispatcher("billadd.jsp").forward(req, resp);
        }


    }

    /**
     * 点击查看，按钮弹出查看界面
     * @param req
     * @param resp
     */
 private void view(HttpServletRequest req, HttpServletResponse resp){
     String id = req.getParameter("billid");
     Bill bill = billService.getBillById(id);
     req.setAttribute("bill",bill);
     try {
         if (bill!=null) {//成功就进入
             req.getRequestDispatcher("billview.jsp").forward(req, resp);
         }else {//失败就跳转回原页面
             req.setAttribute("error", "error");
             resp.sendRedirect("bill.do?method=query");//使用这个方法实现了页面的刷新
         }
     } catch (ServletException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

    /**
     * 点击修改跳出修改页面，返回 id对应的bill对象
     * @param req
     * @param resp
     */
 private  void modify(HttpServletRequest req, HttpServletResponse resp){
     String id = req.getParameter("billid");
     Bill bill = billService.getBillById(id);
     req.setAttribute("bill",bill);
     try {
         if (bill!=null){
             //成功就转发到 修改页面
             req.getRequestDispatcher("billmodify.jsp").forward(req,resp);
         }else {
             //失败就返回billlist 页面
             req.setAttribute("error","error");
             req.getRequestDispatcher("bill.do?method=query").forward(req,resp);
         }
     } catch (ServletException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

    /**
     * 点击修改提交表单执行
     * @param req
     * @param resp
     */
    private  void modifysave(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        String id = req.getParameter("id");
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");//8

        Bill bill = billService.getBillById("id");//获取原生bill

        //修改bill类的值
        bill.setId(Integer.parseInt(id));
        bill.setProductName(productName);
        bill.setBillCode(billCode);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount));
        bill.setTotalPrice(new BigDecimal(totalPrice));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setIsPayment(Integer.parseInt(isPayment));//8

        User modifyUser = (User)req.getSession().getAttribute(Constants01.USER_SERSSION);
        bill.setModifyBy(modifyUser.getId());
        bill.setModifyDate(new Date());//10

        flag = billService.modifyBill(bill);

        if (flag == true) {
            //如果修改成功
            req.setAttribute("message","修改订单成功");
            try {
                req.getRequestDispatcher("bill.do?method=query").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //如果修改失败 返回原界面
            req.setAttribute("error","error");
            try {
                req.getRequestDispatcher("bill.do?method=modify").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *  点击删除按钮触发的方法
     * @param req
     * @param resp
     */
    private  void delbill(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        String id = req.getParameter("billid");
        HashMap<String, String> data = new HashMap<>();

        if (billService.getBillById(id)!=null) {//id存在
            flag = billService.deleteBillById(id);
            if (flag) {
                //如果删除成功
                data.put("delResult","true");
            }else{
                //如果删除失败
                data.put("delResult","false");
            }
        }else{
            //id 不存在
            data.put("delResult","notexist");
        }
        resp.setContentType("application/json");
        ServletOutputStream out =null;
        try {
            out = resp.getOutputStream();
            out.write(JSONArray.toJSONBytes(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //处理异常
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }











}
