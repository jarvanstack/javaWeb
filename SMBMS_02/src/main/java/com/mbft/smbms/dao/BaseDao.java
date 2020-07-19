package com.mbft.smbms.dao;

import com.mbft.smbms.util.jdbcPool.JdbcPool02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 操作数据库的基类，-- 静态类
 * 1.实现获取Connection （通过 =自己制作的数据池）
 * 2.实现查询操作
 * 3. 实现更新（赠删改）
 * 4. 关闭资源（调用JDBCPool)
 */
public class BaseDao {
    /**
     *  1.获取Connection
     * @return 获取Connection
     */
    public static Connection getConnection(){
        return JdbcPool02.getConnection();
    }

    /**
     * 2.执行查询操作获取 ResultSet
     *
     * 因为涉及到，statement的预编译问题，所以传入参数 预编译的数组Object[]
     */
    public static ResultSet executeQuery(PreparedStatement preparedStatement,Object[] params) throws SQLException {
        if (params!=null && params.length>0) {//如果不为空且大于 0
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeQuery();
    }

    /**
     * 3.执行Update操作。
     * @param preparedStatement 预编译的preparedStatement
     * @param params 预编译传入的参数
     * @return int 更新的行数
     */
    public static int executeUpdate(PreparedStatement preparedStatement,Object[] params) throws SQLException {
        if (params!=null && params.length>0) {//如果不为空 且 大于 0
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeUpdate();
    }

    /**
     *  4. 关闭资源，调用JDBCPool的方法，直接关闭资源。
     * @param connection 方
     * @param preparedStatement
     * @param resultSet
     * @return true 即为成功。
     */
    public static boolean closeResources(Connection connection , PreparedStatement preparedStatement, ResultSet resultSet){
        return  JdbcPool02.release(connection,preparedStatement,resultSet);
    }
}
