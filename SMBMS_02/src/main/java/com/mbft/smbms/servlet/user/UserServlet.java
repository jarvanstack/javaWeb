package com.mbft.smbms.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mbft.smbms.pojo.Role;
import com.mbft.smbms.pojo.User;
import com.mbft.smbms.service.role.RoleService;
import com.mbft.smbms.service.role.RoleServiceImpl;
import com.mbft.smbms.service.user.UserService;
import com.mbft.smbms.service.user.UserServiceImpl;
import com.mbft.smbms.util.Constants01;
import com.mbft.smbms.util.StringUtil01;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 这个类实现对用户User的操作。
 * 方法提出来，可以实现复用，和（一个请求jsp/user.do 根据不同的
 * 【method参数值】调用不同的提出来的方法，）
 */
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        //如果传入method的参数是，savepwd就执行的方法，修改密码
        if (method != null) {
            if (method.equals("savepwd")) {
                savepwd(req, resp);
            }
            //如果 pwdmodify 执行前端的旧密码验证
            else if (method.equals("pwdmodify")) {
                pwdmodify(req, resp);
            } else if (method.equals("query")) {
                query(req, resp);
            } else if (method.equals("getrolelist")) {
                getrolelist(req, resp);
            } else if (method.equals("ucexist")) {//ajax检查用户是否已经存在.
                userCodeExist(req, resp);
            } else if (method.equals("add")) {
                add(req, resp);
            } else if (method.equals("view")) {
                //view是通过id参数来获取内容的
                view(req, resp);
            } else if (method.equals("modify")) {
                modify(req, resp);
            } else if (method.equals("modifyexe")) {
                try {
                    modifyexe(req, resp);
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("modifyexe ------- error");
                }
            } else if (method.equals("deluser")) {
                deluser(req, resp);
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
     *
     * @param req
     * @param resp
     */
    private void pwdmodify(HttpServletRequest req, HttpServletResponse resp) {
        Object o = req.getSession().getAttribute(Constants01.USER_SERSSION);
        HashMap<String, String> map = new HashMap<String, String>();
        //session 过期
        if (o == null) {
            map.put(Constants01.RESULT, "sessionerror");
        }
        //旧密码为空
        else if (req.getParameter("oldpassword") == null) {
            map.put(Constants01.RESULT, "error");
        } else {
            User user = (User) o;
            //旧密码不正确
            if (!req.getParameter("oldpassword").equals(user.getUserPassword())) {
                map.put(Constants01.RESULT, "false");
            } else {
                map.put(Constants01.RESULT, "true");
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
        } finally {
            //关闭资源操作
            assert out != null;
            out.close();//关闭资源
        }

    }

    /**
     * 用户查询，userlist，
     * 前端需要的参数
     * 1. queryUserName 防止输入确认后就消失-- 前端传来
     * 2.查询角色类似， -- 前端
     * 3. 角色list                                 --数据库
     * 4. 用户表li                                  --数据库
     * 5. 页数 -- 计算
     * 6. 总页数                                    --数据库
     * 7. 当前页码 -- 前端
     *
     * @param req
     * @param resp
     */
    private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //定义参数
        String queryUserName = "";//1. 查询名 防止输入确认后就消失     -- 前端传来
        String queryUserRole = "";//2.查询角色类似， -- 前端
        List<Role> roleList = null; // 3. 角色list --数据库
        List<User> userList = null; // 4. 用户表list --数据库
        int totalPageCount = 0; //5. 页数 -- 计算
        int totalCount = 0;   //6. 总用户数 --数据库
        String currentPageNo = "";  //7. 当前页码 -- 前端

        //对象
        RoleService roleService = new RoleServiceImpl();
        UserService userService = new UserServiceImpl();


        //加载前端参数
        queryUserName = req.getParameter("queryname");//1.
        System.out.println("queryUserName-->" + queryUserName);
        queryUserRole = req.getParameter("queryUserRole");//2
        currentPageNo = req.getParameter("pageIndex");//7.
        totalCount = userService.getUserCount(queryUserName, queryUserRole);//6
        System.out.println("totalCount--->" + totalCount);
        if (totalCount % Constants01.PAGESIZE != 0) {
            totalPageCount = (totalCount / Constants01.PAGESIZE) + 1;
        } else {
            totalPageCount = (totalCount / Constants01.PAGESIZE);//5.
        }
        //判断前端的页数并取值
        //当前页码为空就 给值为 1
        if (StringUtil01.isNullOrEmpty(currentPageNo)) {
            currentPageNo = "1";
        } else {
            //如果是数字就进行数字判断
            if (StringUtil01.isInteger(currentPageNo)) {
                //小于等于-0就是初始页码
                if (Integer.parseInt(currentPageNo) <= 0) {
                    currentPageNo = "1";

                }//如果大于最大页码就 最后一页。
                else if (Integer.parseInt(currentPageNo) > totalCount) {
                    currentPageNo = "" + totalCount;
                }
            }
        }


        roleList = roleService.getRoleList();
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo);
        // 返回数据
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);
        req.setAttribute("roleList", roleList);
        req.setAttribute("userList", userList);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.getRequestDispatcher("userlist.jsp").forward(req, resp);

    }

    /**
     * getrolelist 获取用户列表 role list .
     * 返回json数据。
     *
     * @param req
     * @param resp
     */
    private void getrolelist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        System.out.println(roleList.toArray());
        //设置返回数据 json，
        resp.setContentType("application/json");
        ServletOutputStream out = resp.getOutputStream();
        out.write(JSONArray.toJSONBytes(roleList));
        out.flush();
        out.close();
    }

    /**
     * 用户管理》》添加用户界面 输入信息后确认后走的方法，method = add ，
     * Servlet层实现User的填充
     *
     * @param req
     * @param resp
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("-->进入add");
        User user = new User();
        String userCode = req.getParameter("userCode");//1.
        String userName = req.getParameter("userName");//2
        String userPassword = req.getParameter("userPassword");//3
        String gender = req.getParameter("gender");//4性别
        Date birthday = null;
        try {//5出生
            birthday = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("--->格式化birthday 异常");
        }
        String phone = req.getParameter("phone");//6
        String address = req.getParameter("address");//7
        String userRole = req.getParameter("userRole");//8


        //赋值user
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setGender(Integer.parseInt(gender));
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(userRole));//8.

        System.out.println("user--->userName " + user.getUserName());
        //新增
        Object attribute = req.getSession().getAttribute(Constants01.USER_SERSSION);
        if (attribute != null) {
            User createBy = (User) attribute;
            user.setCreatedBy(createBy.getId());//添加人的id
            user.setCreationDate(new Date(System.currentTimeMillis()));//现在的添加时间
            user.setModifyBy(createBy.getId());//添加-- 修改人的id
            user.setModifyDate(new Date(System.currentTimeMillis()));//添加-- 修改现在的添加时间
        }
        boolean flag = new UserServiceImpl().addUser(user);
        if (flag) {//如果添加成功,重定向到userlist.jsp 界面
            System.out.println("--->添加成功");
            req.setAttribute("success", "添加成功");
            resp.sendRedirect("userlist.jsp?method=query");
        } else {//如果添加失败 转发到当前页面
            System.out.println("--->添加失败");
            req.setAttribute("fail", "添加失败");
            //
            req.getRequestDispatcher("/jsp/useradd.jsp").forward(req, resp);
        }


    }

    /**
     * ajax检查userCode是否存在
     * addUser 界面，用户账号验证的ajax,
     * 返回数据为json，类型为HashMap
     * userCode : exist 或者 userCode : notexist
     *
     * @param req
     * @param resp
     */
    private void userCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        boolean flag = false;
        String userCode = req.getParameter("userCode");

        //给map调用service给map合适的值，传递参数，userCode
        flag = new UserServiceImpl().userCodeExist(userCode);
        if (flag) {//如果存在就，返回
            map.put("userCode", "exist");
        } else {//不存在，就返回不存在。
            map.put("userCode", "notExist");
        }
        //返回json，map
        resp.setContentType("application/json");
        ServletOutputStream out = null;
        try {
            out = resp.getOutputStream();
            out.write(JSONArray.toJSONBytes(map));
            System.out.println("userCodeExist---> map " + map);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * @param req
     * @param resp
     */
    private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("uid");
        User user = null;
        //不为空 数字大于 0
        if (StringUtil01.isNotNull(id) && StringUtil01.isInteger(id)) {
            user = userService.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("userview.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "error");
            req.getRequestDispatcher("userview.jsp").forward(req, resp);
        }
    }

    /**
     * 通过传入的id ，返回对应的user，并转发到原页面.
     * 点击修改用户信息的时候的请求 modify
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("uid");
        User user = null;
        //如果id，不为空，且有数字。
        if (StringUtil01.isNotNull(id)) {
            user = userService.getUserById(id);
            req.setAttribute("user", user);
            System.out.println("返回数据");
            req.getRequestDispatcher("/jsp/usermodify.jsp").forward(req, resp);
        } else {
            //处理错误信息
            req.setAttribute("error", "error");
            //返回原页面，
            req.getRequestDispatcher("/jsp/userlist.jsp").forward(req, resp);
        }
    }

    /**
     * 用户信息修改
     * 先修改，能修改就修改，重定向userlist.jsp
     * 修改失败就 转发 modify.jsp + user +error
     *
     * @param req
     * @param resp
     */
    private void modifyexe(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException, ServletException {
        //先修改，
        //准备数据
        boolean flag = false;
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");//7
        User newUser = userService.getUserById(id);//这里不要使用newUser，使用 旧的user，
        newUser.setUserName(userName);
        newUser.setGender(Integer.parseInt(gender));
        newUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setUserRole(Integer.parseInt(userRole));//7.
        User modifyUser = (User) req.getSession().getAttribute(Constants01.USER_SERSSION);
        newUser.setModifyBy(modifyUser.getId());
        newUser.setModifyDate(new Date(System.currentTimeMillis()));//9修改人和修改时间

        //修改 flag = userService.modifyUser(newUser)
        flag = userService.modifyUser(newUser);
        if (flag) {//如果修改成功就 重定向
            System.out.println("modify -- success");
            resp.sendRedirect("userlist.jsp?method=query&queryname=&pageIndex=1");
        } else {//如果失败就 error + user + 转发
            System.out.println("modify --> error");
            req.setAttribute("error", "error");
            req.setAttribute("user", userService.getUserById(newUser.getId().toString()));
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }

    }

    /**
     * 删除User执行的 方法.
     *
     * @param req
     * @param resp
     */
    private void deluser(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        String id = req.getParameter("uid");//删除用户的id
        HashMap<String, String> data = new HashMap<>();//前端需要json，就给json，
        //别乱写，前端需要什么，就给什么，前端需要json，就给json，前端需要json，就给json，别乱写，
        //前端需要json，就给json，别乱写

        //执行service的删除 flag = userService.deleteUser(id);

        if (userService.getUserById(id) == null) {
            //如果用户不存在
            data.put("delResult", "notexist");
        } else {//如果用户存在执行删除
            flag = userService.deleteUser(id);
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

}