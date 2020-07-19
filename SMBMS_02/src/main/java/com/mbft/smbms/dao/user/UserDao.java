package com.mbft.smbms.dao.user;

import com.mbft.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


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
     * 改密码操作，返回是否改密成功.
     *
     * @param connection
     * @param params
     * @return
     */
    boolean updatePassword(Connection connection, Object[] params);

    /**
     * 获取条件查询用户的总数。
     * @param connection
     * @param userName 用户查询的名称，支持模糊查询，如果为空，就全查询
     * @param userCode 用户的角色id，比如管理员为1，这个和Role表联系着。
     * @return 条件查询用户的总数。
     */
    int getUserCount(Connection connection, String userName, String userCode);

    /**
     * 获取条件下user list ，
     * 类似getUserCount()的动态SQL
     * 但是比总数多一个条件，当前页码，【要分页查询 limit 0,5】
     * pageSize 可以重Constant那里去拿
     * @param connection
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @return
     */
    List<User> getUserList(Connection connection, String userName,String userRole,String currentPageNo);

    /**
     * 用户添加DAO
     * @param connection
     * @param user
     * @return
     */
    boolean addUser(Connection connection ,User user) throws SQLException;

    /**
     * 调用数据库判断是否存在userCode 是就返回true
     * @param connection
     * @param userCode
     * @return
     */
    boolean userCodeExist(Connection connection,String userCode) throws SQLException;

    /**
     * view查询具体用户，通过你id查询
     * @param connection
     * @param id
     * @return
     */
    User getUserById(Connection connection,String id);

    /**
     * 修改用户modifyUser，
     * @param connection
     * @param user
     * @return
     */
    boolean modifyUser (Connection connection ,User user) throws SQLException;

    /**
     * 删除用户，增删改都需要抛出异常，给service回滚的机会和commit的机会。
     * @param connection
     * @param id
     * @return
     * @throws SQLException
     */
    boolean deleteUser(Connection connection,String id)throws SQLException;
}
