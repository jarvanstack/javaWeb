package com.mbft.smbms.dao.role;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    /**
     * 获取role List集合，获取所有role，只有3个，不需要参数
     *
     * @return 获取role List集合
     */
    @Override
    public List<Role> getRoleList(Connection connection) {
        List<Role> roleList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (connection!=null){
            try {

                String sql = "select * from smbms.smbms_role";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = BaseDao.executeQuery(preparedStatement, null);
                while (resultSet.next()) {
                    Role role = new Role();
                    role.setId(resultSet.getInt("id"));
                    role.setRoleCode(resultSet.getString("roleCode"));
                    role.setRoleName(resultSet.getString("roleName"));
                    role.setCreatedBy(resultSet.getInt("createdBy"));
                    role.setCreationDate(resultSet.getDate("creationDate"));
                    role.setModifyBy(resultSet.getInt("modifyBy"));
                    role.setModifyDate(resultSet.getDate("modifyDate"));
                    roleList.add(role);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                BaseDao.closeResources(connection,preparedStatement,resultSet);
            }
        }else {
            //处理
            System.out.println("RoleDaoImpl-->null connection");
        }
        return roleList;
    }
    @Test // 测试获取bill list
    public void test(){
        Connection connection = BaseDao.getConnection();
        List<Role> roleList = getRoleList(connection);
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
