package com.mbft.smbms.service.user;


import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.dao.user.UserDao;
import com.mbft.smbms.dao.user.UserDaoImpl;
import com.mbft.smbms.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{
    //service层必然会调用DAO层的方法，所以提升到全局。
    private UserDao userDao;
    //在构造器里面对userDao实例化为userDaoImpl
    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    /**
     * 登录业务逻辑，传入参数 userCode userPassword
     * 调用 DAO层 UserDaoImpl的
     * userDao.getLoginUser(connection,params);
     *
     * @param userCode
     * @param userPassword
     * @return
     */
    @Override
    public User login(String userCode, String userPassword) {
        User user ;
        Connection connection;
        connection = BaseDao.getConnection();
        Object[] params = {userCode,userPassword};
        user = userDao.getLoginUser(connection,params);
        if (connection != null){
            BaseDao.closeResources(connection,null,null);
        }
        //二次判断，如果密码不匹配就为空
        if (user!=null){
            if (!user.getUserPassword().equals(userPassword)) {
                user = null;
            }
        }
        return user;
    }

    /**
     * 传入id和需要修改的密码，就给服务层级修改。
     *
     * @param id
     * @param userNewPassword
     * @return
     */
    @Override
    public boolean updatePassword(int id, String userNewPassword) {
        Object[] params = {userNewPassword,id};
        Connection connection = BaseDao.getConnection();
        return userDao.updatePassword(connection,params);
    }

    /**
     * 返回条件下用户的总数
     * 前端参数为
     * queryname=&queryUserRole=0&pageIndex=1
     * 调用Dao层的数据，dao层 需要connection，String userName,String userRole
     * @param userName queryname 查询用户名称，前端参数为
     * @param userRole queryUserRole 查询的用户角色
     * @return 返回条件下用户的总数
     */
    @Override
    public int getUserCount(String userName, String userRole) {
        Connection connection = null;
        connection = BaseDao.getConnection();
        if (connection!=null){
            BaseDao.closeResources(connection,null,null);
        }
        return userDao.getUserCount(connection,userName,userRole);
    }

    /**
     * Service获取user List,传入参数为 username userRole currentPageNo用于分页。
     *
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @return
     */
    @Override
    public List<User> getUserList(String userName, String userRole, String currentPageNo) {
        Connection connection = BaseDao.getConnection();
        List<User> userList = null;
        if (connection!=null){
            userList = userDao.getUserList(connection, userName, userRole, currentPageNo);
        }
        BaseDao.closeResources(connection,null,null);
        return userList;
    }
    @Test//测试service方法
    public void testGetUserList(){
        Connection connection = BaseDao.getConnection();
        String userName = "理";
        String userRole = "";
        List<User> userList = getUserList( userName, userRole, "");
        for (User user : userList) {
            System.out.println("name: "+user.getUserName());
        }
    }

    @Override
    public boolean addUser(User user) {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);//开启事务,开启事务但是你没有提交，你个傻逼

            flag = userDao.addUser(connection,user);
            //没有异常就记得提交
            //记得提交
            //记得提交
            connection.commit();
        } catch (SQLException e) {
            //异常就回滚
            System.out.println("serviceException---rollback");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 调用数据库判断是否存在userCode 是就返回true
     *
     * @param userCode
     * @return
     */
    @Override
    public boolean userCodeExist(String userCode) {
        boolean flag =false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);//开启事务，失败回滚
            flag = userDao.userCodeExist(connection,userCode);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * view查询具体用户，通过你id查询
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(String id) {
        Connection connection = BaseDao.getConnection();
        User user = null;
        user = userDao.getUserById(connection,id);
        BaseDao.closeResources(connection,null,null);
        return user;
    }

    /**
     * 修改用户modifyUser，增删改，都需要事务
     *
     * @param user
     * @return
     */
    @Override
    public boolean modifyUser(User user)  {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);//开启事务
            flag = userDao.modifyUser(connection,user);
            connection.commit();//【】又忘记了提交，又忘记了提交，又忘记了提交。commit commit commit commit
            //commit commit commit commit commit commit commit commit commit commit commit
            //commit commit commit commit commit commit commit commit commit commit commit
            //commit commit commit commit commit commit commit commit commit commit commit
        } catch (SQLException e) {
            e.printStackTrace();
            //异常回滚
            try {
                connection.rollback();
                flag = false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            BaseDao.closeResources(connection,null,null);
        }
        return flag;
    }

    /**
     * 删除用户，增删改都需要抛出异常，给service回滚的机会和commit的机会。
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deleteUser(String id) {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);//开启事务，记得commit
            flag = userDao.deleteUser(connection,id);
            connection.commit();//成功记得commit commit commit commit commit commit
        } catch (SQLException e) {
            e.printStackTrace();
            //错误，回滚
            try {
                connection.rollback();
                flag = false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            BaseDao.closeResources(connection,null,null);
        }

        return flag;
    }
    @Test//测试删除用户 deleteUser
    public void testdeleteUser() throws SQLException {
        System.out.println(deleteUser( "26"));
    }
    @Test//测试修改用户modifyUser()
    public void testmodifyUser() throws SQLException {
        User userById = getUserById( "2");
        userById.setUserName("傻逼");
        System.out.println(modifyUser( userById));
    }

    @Test//测试获取具体 的用户信息的getUserById
    public void test01() throws SQLException {
        User userById = getUserById( "1");
        System.out.println(userById.getUserName());
    }
    /**
     * 测试service层是否可用
     */
    @Test
    public void test(){
        System.out.println(userCodeExist("admin"));
    }
}
