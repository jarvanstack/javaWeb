package com.mbft.smbms.dao.user;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.pojo.User;
import com.mbft.smbms.util.Constants01;
import com.mbft.smbms.util.StringUtil01;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取条件查询用户的总数。这个预编译的动态SQL是重难点。
     *
     * @param connection
     * @param userName   用户查询的名称，支持模糊查询，如果为空，就全查询
     * @param userRole   用户的角色id，比如管理员为1，这个和Role表联系着。
     * @return 条件查询用户的总数。
     */
    @Override
    public int getUserCount(Connection connection, String userName, String userRole) {
        int userCount = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        //上面传下来的的connection不能为空,才能执行查询.
        if (connection != null) {
            try {
                StringBuilder sql = new StringBuilder();
                sql.append("select count(1) as userCount from smbms.smbms_user ,smbms.smbms_role where smbms_user.userRole = smbms_role.id ");
                //如果【不】为空，或者 “” “ ” 等就
                if (!StringUtil01.isNullOrEmpty(userName)) {
                    sql.append(" and smbms_user.userName like ?");
                    arrayList.add("%" + userName + "%");
                }
                if (!StringUtil01.isNullOrEmpty(userRole)) {
                    //这里的userCode的范围？1,2,3，
                    sql.append(" and userRole = ? ");
                    arrayList.add(userRole);//String 试试
                }
                preparedStatement = connection.prepareStatement(sql.toString());
                resultSet = BaseDao.executeQuery(preparedStatement, arrayList.toArray());
                while (resultSet.next()) {
                    userCount = resultSet.getInt("userCount");
                    System.out.println("userCount -- " + userCount);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //处理为空的情况
            System.out.println("UserDaoImpl ---- connection == null");
        }

        return userCount;
    }

    @Test //测试类，测试获得总数的方法的问题，是对的？？
    public void testgetUserCount() {
        Connection connection = BaseDao.getConnection();
        String userName = "理";
        String userRole = "";
        System.out.println(getUserCount(connection, userName, userRole));
    }

    /**
     * 获取条件下user list ，
     * 类似getUserCount()的动态SQL
     * 但是比总数多一个条件，当前页码，【要分页查询】
     * pageSize 可以重Constant那里去拿
     *
     * @param connection
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @return
     */
    @Override
    public List<User> getUserList(Connection connection, String userName, String userRole, String currentPageNo) {
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<Object> params = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder sql = new StringBuilder("select * from smbms.smbms_user ,smbms.smbms_role where smbms_user.userRole = smbms_role.id ");
        //如果不为空才添加
        if (!StringUtil01.isNullOrEmpty(userName)) {

            sql.append(" and smbms_user.userName like ? ");
            params.add("%" + userName + "%");
        }
        //如果不为空才添加
        if (!StringUtil01.isNullOrEmpty(userRole)) {
            sql.append(" and smbms_user.userRole = ? ");
            params.add(userRole);
        }
        //currentPageNo 需要判断，在Servlet里进行，这里就不赘述了。【算了万一Servlet不判不规范这里多一层】
        //算了没有最大页数，不方便。给Servlet层解决。
        //如果是数字，就继续判断
        //分页的操作是 (limit 当前条数，每页条数 )
        if (!StringUtil01.isNullOrEmpty(currentPageNo) && StringUtil01.isInteger(currentPageNo) && Integer.parseInt(currentPageNo) > 0) {
            sql.append(" limit ? , ? ");
            params.add((Integer.parseInt(currentPageNo) - 1) * Constants01.PAGESIZE);
            params.add(Constants01.PAGESIZE);
        } else {
            sql.append(" limit ? , ? ");
            params.add(0);
            params.add(Constants01.PAGESIZE);
        }

        try {
            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = BaseDao.executeQuery(preparedStatement, params.toArray());
            while (resultSet.next()) {
                User _user = new User();
                _user.setId(resultSet.getInt("id"));
                _user.setUserCode(resultSet.getString("userCode"));
                _user.setUserName(resultSet.getString("userName"));
                _user.setGender(resultSet.getInt("gender"));
                _user.setBirthday(resultSet.getDate("birthday"));
                _user.setPhone(resultSet.getString("phone"));
                _user.setUserRole(resultSet.getInt("userRole"));
                _user.setUserRoleName(resultSet.getString("roleName"));
                userList.add(_user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, preparedStatement, resultSet);
        }
        return userList;
    }

    @Test//测试getUserList 的 模糊查询的问题
    public void testGetUserList() {
        Connection connection = BaseDao.getConnection();
        String userName = "理";
        String userRole = "";
        List<User> userList = getUserList(connection, userName, userRole, "");
        for (User user : userList) {
            System.out.println("name: " + user.getUserName());
        }
    }

    /**
     * 用户添加DAO
     *
     * @param connection
     * @param user
     * @return
     */
    @Override
    public boolean addUser(Connection connection, User user) throws SQLException {
        boolean flag = false;
        //上级的connection未知，所以要判断.
        if (connection != null) {
            ArrayList<Object> params = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO `smbms`.`smbms_user`(`userCode`, `userName`, `userPassword`, `gender`, `birthday`, `phone`, `address`, `userRole`, `createdBy`, `creationDate`, `modifyBy`, `modifyDate`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            //这个需要回滚的异常，必须向上抛出，给service层开启事务
            //给params 添加 param
            params.add(user.getUserCode());
            params.add(user.getUserName());
            params.add(user.getUserPassword());
            params.add(user.getGender());
            params.add(user.getBirthday());
            params.add(user.getPhone());
            params.add(user.getAddress());
            params.add(user.getUserRole());
            //新增
            params.add(user.getCreatedBy());
            params.add(user.getCreationDate());
            params.add(user.getModifyBy());
            params.add(user.getModifyDate());

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
            if (i > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 调用数据库判断是否存在userCode 是就返回true
     *
     * @param connection
     * @param userCode
     * @return
     */
    @Override
    public boolean userCodeExist(Connection connection, String userCode) throws SQLException {
        boolean flag = false;
        if (connection != null) {
            ArrayList<Object> params = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            StringBuilder sql = new StringBuilder();
            if (StringUtil01.isNotNull(userCode)) {//如果不是空，哎这个几次了
                String sql2 = "SELECT\n" +
                        "\t*\n" +
                        "FROM\n" +
                        "\tsmbms.smbms_user \n" +
                        "WHERE\n" +
                        "\tsmbms.smbms_user.userCode = ?";
                sql.append(sql2);
            }
            params.add(userCode);


            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = BaseDao.executeQuery(preparedStatement, params.toArray());
            if (resultSet.next()) {//如果resultSet 有值，就返回true
                flag = true;
            }

            BaseDao.closeResources(connection, preparedStatement, resultSet);


        }
        return flag;
    }

    /**
     * view查询具体用户，通过你id查询
     *
     * @param connection
     * @param id
     * @return
     */
    @Override
    public User getUserById(Connection connection, String id) {
        User user = new User();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            //不为空且为数字
            if (StringUtil01.isNotNull(id) && StringUtil01.isInteger(id)) {
                String sql = "SELECT\n" +
                        "\tsmbms.smbms_user.*\n" +
                        "FROM\n" +
                        "\tsmbms.smbms_user\n" +
                        "WHERE\n" +
                        "\tsmbms_user.id = ?";
                Object[] params = {id};
                try {
                    preparedStatement = connection.prepareStatement(sql);
                    resultSet = BaseDao.executeQuery(preparedStatement, params);
                    while (resultSet.next()) {
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
                }
            }
        }
        return user;
    }

    /**
     * 修改用户modifyUser，
     *
     * @param connection
     * @param user
     * @return
     */
    @Override
    public boolean modifyUser(Connection connection, User user) throws SQLException {
        boolean flag = false;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            ArrayList<Object> params = new ArrayList<>();
            String sql = "UPDATE `smbms`.`smbms_user` \n" +
                    "SET `userCode` = ? ,\n" +
                    "`userName` = ?,\n" +
                    "`userPassword` = ?,\n" +
                    "`gender` = ?,\n" +
                    "`birthday` = ?,\n" +
                    "`phone` = ?,\n" +
                    "`address` = ?,\n" +
                    "`userRole` = ?,\n" +
                    "`creationDate` = ?,\n" +
                    "`modifyBy` = ?,\n" +
                    "`modifyDate` = ? \n" +
                    "WHERE\n" +
                    "\t`id` = ?";//12
            params.add(user.getUserCode());
            params.add(user.getUserName());
            params.add(user.getUserPassword());
            params.add(user.getGender());
            params.add(user.getBirthday());
            params.add(user.getPhone());
            params.add(user.getAddress());
            params.add(user.getUserRole());
            params.add(user.getCreationDate());
            params.add(user.getModifyBy());
            params.add(user.getModifyDate());
            params.add(user.getId());//12
            preparedStatement = connection.prepareStatement(sql);
            int i = BaseDao.executeUpdate(preparedStatement,params.toArray());
            if (i > 0) {
                flag = true;
            }
            BaseDao.closeResources(connection,preparedStatement,null);
        }
        return flag;
    }

    /**
     * 删除用户，增删改都需要抛出异常，给service回滚的机会和commit的机会。
     *
     * @param connection
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deleteUser(Connection connection, String id) throws SQLException {
        boolean flag = false;
        PreparedStatement preparedStatement  = null;
        String sql = "DELETE FROM `smbms`.`smbms_user` WHERE `id` = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        preparedStatement = connection.prepareStatement(sql);

        int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
        if (i > 0) {
            flag = true;
        }

        return flag;
    }
    @Test//测试删除用户 deleteUser
    public void testdeleteUser() throws SQLException {
        System.out.println(deleteUser(BaseDao.getConnection(), "27"));
    }
    @Test//测试修改用户modifyUser()
    public void testmodifyUser() throws SQLException {
        User userById = getUserById(BaseDao.getConnection(), "2");
        userById.setUserName("李亚洲老坑比");
        System.out.println(modifyUser(BaseDao.getConnection(), userById));
    }

    @Test//测试获取具体 的用户信息的getUserById
    public void test01() throws SQLException {
        User userById = getUserById(BaseDao.getConnection(), "1");
        System.out.println(userById.getUserName());
    }

    @Test
    public void test() throws SQLException {
        Object[] params = {"admin", "1234567"};
        User user = getLoginUser(BaseDao.getConnection(), params);
        user.setUserCode("test01");
        System.out.println(addUser(BaseDao.getConnection(), user));
    }

}
