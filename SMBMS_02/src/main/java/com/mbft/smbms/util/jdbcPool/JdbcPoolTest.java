package com.mbft.smbms.util.jdbcPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这个类用来测试jdbcPool的是否可用
 */
public class JdbcPoolTest {
    public static void main(String[] args) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        String sql = "select * from smbms.smbms_user where userCode = ? and userPassword = ?";
        try {
            connection = JdbcPool02.getConnection();
            preStatement = connection.prepareStatement(sql);
            preStatement.setString(1,"admin");
            preStatement.setString(2,"1234567");
            resultSet = preStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getObject("userName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭链接
        }

    }
}
