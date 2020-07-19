package com.mbft.smbms.service.provider;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.dao.provider.ProviderDao;
import com.mbft.smbms.dao.provider.ProviderDaoImpl;
import com.mbft.smbms.pojo.Provider;
import org.junit.Test;

import java.sql.Connection;
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

    @Test // 测试获取 provider list getProviderList
    public void testgetProviderList(){
        List<Provider> providerList = getProviderList();
        for (Provider provider : providerList) {
            System.out.println(provider.getProName());
        }
    }
}
