package com.mbft.smbms.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 对BaseDao的测试。
 */
public class BaseDaoTest {
    public static void main(String[] args) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = BaseDao.getConnection();
            String sql = "select * from smbms.smbms_user where userCode = ? and userPassword = ?";
            preparedStatement= connection.prepareStatement(sql);
            Object[] params = {"admin","123456"};
            resultSet = BaseDao.executeQuery(preparedStatement, params);
            while (resultSet.next()){
                System.out.println(resultSet.getString("userName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
