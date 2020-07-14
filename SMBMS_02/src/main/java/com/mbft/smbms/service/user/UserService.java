package com.mbft.smbms.service.user;

import com.mbft.smbms.pojo.User;

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
     User login(String userCode,String userPassword);

    /**
     * 传入id和需要修改的密码，就给服务层级修改。
     * @param id
     * @param userNewPassword
     * @return
     */
    boolean updatePassword(int id,String userNewPassword);
}
