package com.mbft.smbms.dao.user;

import com.mbft.smbms.pojo.User;

import java.sql.Connection;


/**
 * 接口
 */
public interface UserDao {
    /**
     * 获取登录的User，
     * @param connection connection
     * @param params 这里使用数组，方便以后拓展,
     *               数组为账号userCode和密码userPassword
     *
     * @return User对象
     */
     User getLoginUser(Connection connection, Object[] params);

    /**
     *  改密码操作，返回是否改密成功.
     * @param connection
     * @param params
     * @return
     */
     boolean updatePassword(Connection connection ,Object[] params);
}
