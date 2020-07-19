package com.mbft.smbms.service.provider;

import com.mbft.smbms.pojo.Provider;

import java.util.List;

public interface ProviderService {
    /**
     * 获取 provider list
     * @return
     */
    List<Provider> getProviderList();
    Provider getProviderById(String id);

    /**
     * providerlist.jsp 页面展示的条件查询
     * @param provider
     * @return
     */
    List<Provider> getProviderListByParams( Provider provider);

    /**
     * 修改Provider,事务，回滚,commit
     * @param provider
     * @return
     */
    boolean modifyProvider(Provider provider);
    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteProviderById(String id);

    /**
     * add Provider
     * 事务 rollback commit
     * @param provider
     * @return
     */
    boolean addProvider(Provider provider) ;
}
