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
            }
        }
        return providerList;
    }

    @Test // 测试 参数获取 provider list \
    //getProviderListByParams
    public void test(){
        Provider provider = new Provider();
        provider.setProName("公");
        provider.setProCode("");
        List<Provider> providerList = getProviderListByParams(BaseDao.getConnection(), provider);
        for (Provider provider1 : providerList) {
            System.out.println(provider1.getProName());
        }
    }
}
