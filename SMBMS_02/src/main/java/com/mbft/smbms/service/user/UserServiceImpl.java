package com.mbft.smbms.service.user;


import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.dao.user.UserDao;
import com.mbft.smbms.dao.user.UserDaoImpl;
import com.mbft.smbms.pojo.User;
import org.junit.Test;

import java.sql.Connection;

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
     * 测试service层是否可用
     */
    @Test
    public void test(){
        System.out.println(new UserServiceImpl().login("root", "612001"));
    }
}
