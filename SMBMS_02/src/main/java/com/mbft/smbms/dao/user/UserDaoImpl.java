package com.mbft.smbms.dao.user;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDaoImpl实现，
 * 1.
 */
public class UserDaoImpl implements UserDao {
    /**
     * 获取登录的User，传入参数 userCode userPassword,
     * 调用BaseDao传入参数 preparedStatement,Object[] params
     * @param connection connection
     * @param params 这里使用数组，方便以后拓展,
     *               数组为账号userCode和密码userPassword
     *
     * @return User对象
     */
    @Override
    public User getLoginUser(Connection connection, Object[] params) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            String sql = "select * from smbms.smbms_user where userCode =? and userPassword = ?";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = BaseDao.executeQuery(preparedStatement, params);
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getDate("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getDate("modifyDate"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResources(connection,preparedStatement,resultSet);
        }
        return user;
    }

    /**
     * 改密码操作，返回是否改密成功.
     *
     * @param connection
     * @param params
     * @return
     */
    @Override
    public boolean updatePassword(Connection connection, Object[] params) {
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        //如果上一层不是你写的，你没把握就要判断 connection 不为空。
        if(connection!=null) {
            String sql = "update smbms.smbms_user set userPassword = ? where id = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                //如果执行的条数大于 0 就 flag = true 。
                if (BaseDao.executeUpdate(preparedStatement,params)>0){
                    flag = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //关闭资源
                BaseDao.closeResources(connection, null, null);
            }
        }
        return flag;
    }

}
