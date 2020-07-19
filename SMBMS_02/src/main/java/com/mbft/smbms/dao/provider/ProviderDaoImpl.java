package com.mbft.smbms.dao.provider;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.pojo.Provider;
import com.mbft.smbms.util.StringUtil01;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl implements ProviderDao {
    /**
     * 获取 provider list
     *
     * @param connection
     * @return
     */
    @Override
    public List<Provider> getProviderList(Connection connection) {

        List<Provider> providersList = new ArrayList<>();
        if (connection!=null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String sql = "SELECT\n" +
                    "\tsmbms.smbms_provider.*\n" +
                    "FROM\n" +
                    "\tsmbms.smbms_provider";
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = BaseDao.executeQuery(preparedStatement, null);
                while (resultSet.next()) {
                    Provider provider = new Provider();
                    provider.setId(resultSet.getInt("id"));
                    provider.setProCode(resultSet.getString("proCode"));
                    provider.setProName(resultSet.getString("proName"));
                    provider.setProDesc(resultSet.getString("proDesc"));
                    provider.setProContact(resultSet.getString("proContact"));
                    provider.setProPhone(resultSet.getString("proPhone"));
                    provider.setProAddress(resultSet.getString("proAddress"));
                    provider.setProFax(resultSet.getString("proFax"));
                    provider.setCreatedBy(resultSet.getInt("createdBy"));
                    provider.setCreationDate(resultSet.getDate("creationDate"));
                    provider.setModifyDate(resultSet.getDate("modifyDate"));
                    provider.setModifyBy(resultSet.getInt("modifyBy"));
                    providersList.add(provider);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResources(connection, preparedStatement, resultSet);
            }
        }
        return providersList;
    }



    @Test // 测试获取 provider list getProviderList
    public void testgetProviderList(){
        List<Provider> providerList = getProviderList(BaseDao.getConnection());
        for (Provider provider : providerList) {
            System.out.println(provider.getProName());
        }
    }

    /**
     * 通过id，获取Provider
     *
     * @param connection
     * @param id
     * @return
     */
    @Override
    public Provider getProviderById(Connection connection, String id) {
        Provider provider = new Provider();
        if (connection!=null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String baseSql = "SELECT * FROM `smbms`.`smbms_provider` where id = ? ";
            StringBuilder sql = new StringBuilder();
            sql.append(baseSql);
            ArrayList<Object> params = new ArrayList<>();
            params.add(id);
            try {
                preparedStatement = connection.prepareStatement(sql.toString());
                resultSet = BaseDao.executeQuery(preparedStatement,params.toArray());
                while (resultSet.next()) {
                    provider.setId(resultSet.getInt("id"));
                    provider.setProCode(resultSet.getString("proCode"));
                    provider.setProName(resultSet.getString("proName"));
                    provider.setProDesc(resultSet.getString("proDesc"));
                    provider.setProContact(resultSet.getString("proContact"));
                    provider.setProPhone(resultSet.getString("proPhone"));
                    provider.setProAddress(resultSet.getString("proAddress"));
                    provider.setProFax(resultSet.getString("proFax"));
                    provider.setCreatedBy(resultSet.getInt("createdBy"));
                    provider.setCreationDate(resultSet.getDate("creationDate"));
                    provider.setModifyDate(resultSet.getDate("modifyDate"));
                    provider.setModifyBy(resultSet.getInt("modifyBy"));//12
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return provider;
    }

    /**
     * providerlist.jsp 页面展示的条件查询
     *
     * @param connection
     * @param provider
     * @return
     */
    @Override
    public List<Provider> getProviderListByParams(Connection connection, Provider provider) {
        List<Provider> providerList = new ArrayList<>();
        if (connection!=null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String baseSql = "SELECT\n" +
                    "\tsmbms.smbms_provider.*\n" +
                    "FROM\n" +
                    "\tsmbms.smbms_provider  where 1=1 ";
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> params = new ArrayList<>();

            sql.append(baseSql);

            if (StringUtil01.isNotNull(provider.getProCode())){
                //如果
                sql.append(" and smbms_provider.proCode like ? ");
                params.add("%"+provider.getProCode()+"%");
            }
            if (StringUtil01.isNotNull(provider.getProName())){
                sql.append(" and smbms_provider.proName like ? ");
                params.add("%"+provider.getProName()+"%");
            }
            System.out.println("sql--->"+sql.toString());
            try {
                preparedStatement = connection.prepareStatement(sql.toString());
                resultSet = BaseDao.executeQuery(preparedStatement,params.toArray());
                while (resultSet.next()) {
                    Provider _provider = new Provider();
                    _provider.setId(resultSet.getInt("id"));
                    _provider.setProCode(resultSet.getString("proCode"));
                    _provider.setProName(resultSet.getString("proName"));
                    _provider.setProDesc(resultSet.getString("proDesc"));
                    _provider.setProContact(resultSet.getString("proContact"));
                    _provider.setProPhone(resultSet.getString("proPhone"));
                    _provider.setProAddress(resultSet.getString("proAddress"));
                    _provider.setProFax(resultSet.getString("proFax"));
                    _provider.setCreatedBy(resultSet.getInt("createdBy"));
                    _provider.setCreationDate(resultSet.getDate("creationDate"));
                    _provider.setModifyDate(resultSet.getDate("modifyDate"));
                    _provider.setModifyBy(resultSet.getInt("modifyBy"));
                    providerList.add(_provider);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResources(connection, preparedStatement, resultSet);
            }
        }
        return providerList;
    }

    /**
     * 修改Provider
     *
     * @param connection
     * @param provider
     * @return
     */
    @Override
    public boolean modifyProvider(Connection connection, Provider provider) throws SQLException {
        boolean flag = false;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> params = new ArrayList<>();
            String baseSql = "UPDATE `smbms`.`smbms_provider` \n" +
                    "SET `proCode` = ?,\n" +
                    "`proName` = ?,\n" +
                    "`proDesc` = ?,\n" +
                    "`proContact` = ?,\n" +
                    "`proPhone` = ?,\n" +
                    "`proAddress` = ?,\n" +
                    "`proFax` = ?,\n" +
                    "`createdBy` = ?,\n" +
                    "`creationDate` = ?,\n" +
                    "`modifyDate` = ?,\n" +
                    "`modifyBy` = ? \n" +
                    "WHERE\n" +
                    "\t`id` = ? "; // 12 加上 最后id params = 12
            sql.append(baseSql);
            params.add(provider.getProCode());
            params.add(provider.getProName());
            params.add(provider.getProDesc());
            params.add(provider.getProContact());
            params.add(provider.getProPhone());
            params.add(provider.getProAddress());
            params.add(provider.getProFax());
            params.add(provider.getCreatedBy());
            params.add(provider.getCreationDate());
            params.add(provider.getModifyDate());
            params.add(provider.getModifyBy()); // 11
            params.add(provider.getId()); // 12


            preparedStatement = connection.prepareStatement(sql.toString());
            int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
            if (i > 0) {
                flag = true;
            }
            BaseDao.closeResources(connection,preparedStatement,null);

        }
        return flag;
    }

    /**
     * 删除
     *
     * @param connection f
     * @param id d
     * @return d
     */
    @Override
    public boolean deleteProviderById(Connection connection, String id) throws SQLException {
        boolean flag = false;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            ArrayList<Object> params = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            String baseSql = "DELETE FROM `smbms`.`smbms_provider` WHERE `id` = ?";
            sql.append(baseSql);
            params.add(id);
            //向上抛出 事务，rollback commit
            preparedStatement = connection.prepareStatement(sql.toString());
            int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
            if (i > 0) {
                flag = true;
            }
        }

        return  flag;
    }

    /**
     * 修改Provider
     *
     * @param connection
     * @param provider
     * @return
     */
    @Override
    public boolean addProvider(Connection connection, Provider provider) throws SQLException {
        boolean flag = false;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> params = new ArrayList<>();
            String baseSql = "INSERT INTO `smbms`.`smbms_provider` ( `proCode`, `proName`, `proDesc`, `proContact`, `proPhone`, `proAddress`, `proFax`, `createdBy`, `creationDate`, `modifyDate`, `modifyBy` )\n" +
                    "VALUES\n" +
                    "\t(\n" +
                    "\t\t ?,\n" +
                    "\t\t ?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t?,\n" +
                    "\t?)"; // 11 id自动生成
            sql.append(baseSql);
            params.add(provider.getProCode());
            params.add(provider.getProName());
            params.add(provider.getProDesc());
            params.add(provider.getProContact());
            params.add(provider.getProPhone());
            params.add(provider.getProAddress());
            params.add(provider.getProFax());
            params.add(provider.getCreatedBy());
            params.add(provider.getCreationDate());
            params.add(provider.getModifyDate());
            params.add(provider.getModifyBy()); // 11
//            params.add(provider.getId()); // 12 id 自动生成


            preparedStatement = connection.prepareStatement(baseSql);
            int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
            if (i > 0) {
                flag = true;
            }
            BaseDao.closeResources(connection,preparedStatement,null);

        }
        return flag;
    }


    @Test // 添加
    public void test() throws SQLException {
        Provider providerById = getProviderById(BaseDao.getConnection(), "5");
        providerById.setProName("半亩方塘");
        boolean b = addProvider(BaseDao.getConnection(), providerById);
        System.out.println(b);
    }
}
