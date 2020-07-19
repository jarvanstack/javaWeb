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
}
