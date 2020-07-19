package com.mbft.smbms.service.provider;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.dao.provider.ProviderDao;
import com.mbft.smbms.dao.provider.ProviderDaoImpl;
import com.mbft.smbms.pojo.Provider;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProviderServiceImpl implements ProviderService {
    ProviderDao providerDao ;

    public ProviderServiceImpl() {
        providerDao = new ProviderDaoImpl();
    }

    /**
     * 获取 provider list
     *
     * @return
     */
    @Override
    public List<Provider> getProviderList() {
        List<Provider> providerList = null;
        Connection connection = BaseDao.getConnection();
        providerList = providerDao.getProviderList(connection);
        BaseDao.closeResources(connection,null,null);
        return providerList;
    }

    @Override
    public Provider getProviderById(String id) {
        Provider provider = new Provider();
        Connection connection = BaseDao.getConnection();
        provider = providerDao.getProviderById(connection,id);
        BaseDao.closeResources(connection,null,null);
        return provider;
    }
    @Test //
    public void testgetProviderById(){
        Provider providerById = getProviderById("1");
        System.out.println(providerById.getProName());

    }
    /**
     * providerlist.jsp 页面展示的条件查询
     *
     * @param provider
     * @return
     */
    @Override
    public List<Provider> getProviderListByParams(Provider provider) {
        List<Provider> providerList= null;
        Connection connection = BaseDao.getConnection();
        providerList = providerDao.getProviderListByParams(connection,provider);

        BaseDao.closeResources(connection,null,null);

        return providerList;
    }

    /**
     * 修改Provider,事务，回滚,commit
     *
     * @param provider
     * @return
     */
    @Override
    public boolean modifyProvider(Provider provider) {
        boolean flag= false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);

            flag = providerDao.modifyProvider(connection,provider);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            //异常回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            BaseDao.closeResources(connection,null,null);
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteProviderById(String id) {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();

        try {
            connection.setAutoCommit(false);
            flag = providerDao.deleteProviderById(connection,id);

            connection.commit();//无异常提交
        } catch (SQLException e) {
            e.printStackTrace();
            //异常回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * add Provider
     * 事务 rollback commit
     *
     * @param provider
     * @return
     */
    @Override
    public boolean addProvider(Provider provider) {
        boolean flag= false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);

            flag = providerDao.addProvider(connection,provider);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            //异常回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            BaseDao.closeResources(connection,null,null);
        }
        return flag;
    }

    @Test // 测试 添加

    public void test(){
        Provider providerById = getProviderById( "5");
        providerById.setProName("service");
        boolean b = addProvider(providerById);
        System.out.println(b);
    }

    @Test // 测试获取 provider list getProviderList
    public void testgetProviderList(){
        List<Provider> providerList = getProviderList();
        for (Provider provider : providerList) {
            System.out.println(provider.getProName());
        }
    }
}
