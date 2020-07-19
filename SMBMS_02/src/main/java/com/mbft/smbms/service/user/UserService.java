package com.mbft.smbms.service.user;

import com.mbft.smbms.pojo.User;

import java.sql.SQLException;
import java.util.List;

/**
 * service层调用DAO层和事务实现业务逻辑
 */
public interface UserService {
    /**
     * 实现登录业务逻辑
     * @param userCode
     * @param userPassword
     * @return User
     */
    User login(String userCode, String userPassword);

    /**
     * 传入id和需要修改的密码，就给服务层级修改。
     *
     * @param id
     * @param userNewPassword
     * @return
     */
    boolean updatePassword(int id, String userNewPassword);

    /**
     * 返回条件下用户的总数
     * 前端参数为
     * queryname=&queryUserRole=0&pageIndex=1
     *
     * @param userName queryname 查询用户名称，前端参数为
     * @param userRole 查询的用户角色
     * @return 返回条件下用户的总数
     */
    int getUserCount(String userName, String userRole);

    /**
     *  Service获取user List,传入参数为 username userRole currentPageNo用于分页。
     *
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @return
     */
    List<User> getUserList(String userName,String userRole,String currentPageNo);

    /**
     * 添加用户，
     * Servlet层完成User的初始化，
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 调用数据库判断是否存在userCode 是就返回true
     * @param userCode
     * @return
     */
    boolean userCodeExist(String userCode);

    /**
     * view查询具体用户，通过你id查询
     * @param id
     * @return
     */
    User getUserById( String id);

    /**
     * 修改用户modifyUser，增删改，都需要事务
     * 不要抛异常
     * @param user
     * @return
     */
    boolean modifyUser ( User user) ;

    /**
     * 删除用户，增删改都需要抛出异常，给service回滚的机会和commit的机会。
     * @param id
     * @return
     * @throws SQLException
     */
    boolean deleteUser(String id);
}
