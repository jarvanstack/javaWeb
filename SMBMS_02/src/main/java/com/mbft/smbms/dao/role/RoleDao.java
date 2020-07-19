package com.mbft.smbms.dao.role;

import com.mbft.smbms.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {
    /**
     * 获取role List集合，获取所有role，只有3个，不需要参数
     * @return 获取role List集合
     */
    List<Role> getRoleList(Connection connection);
}
