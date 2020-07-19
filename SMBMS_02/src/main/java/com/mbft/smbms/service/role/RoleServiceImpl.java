package com.mbft.smbms.service.role;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.dao.role.RoleDao;
import com.mbft.smbms.dao.role.RoleDaoImpl;
import com.mbft.smbms.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    RoleDao  roleDao ;

    public RoleServiceImpl() {
        //实例化
        roleDao = new RoleDaoImpl();
    }

    /**
     * 获取Role list集合,因为查询所有3个role，所以不需要参数
     * 但是还是记得创立connection，控制事务等，并传递给Dao层
     */
    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = new ArrayList<>();
        Connection connection = BaseDao.getConnection();
        roleList = roleDao.getRoleList(connection);
        BaseDao.closeResources(connection,null,null);
        return roleList;
    }
    @Test
    public void test(){
        List<Role> roleList = getRoleList();
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
