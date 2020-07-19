package com.mbft.smbms.service.bill;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.dao.bill.BillDao;
import com.mbft.smbms.dao.bill.BillDaoImpl;
import com.mbft.smbms.dao.provider.ProviderDao;
import com.mbft.smbms.dao.provider.ProviderDaoImpl;
import com.mbft.smbms.pojo.Bill;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BillServiceImpl implements BillService{
    BillDao billDao ;
    ProviderDao providerDao ;

    public BillServiceImpl() {
        billDao = new BillDaoImpl();
        providerDao = new ProviderDaoImpl();
    }

    /**
     * 获取bill list 集合，
     * bill 是前端的查询信息的参数封装
     *
     * @param bill
     * @return
     */
    @Override
    public List<Bill> getBillList(Bill bill) {
        Connection connection = BaseDao.getConnection();
        List<Bill> billList = billDao.getBillList(connection, bill);
        BaseDao.closeResources(connection,null,null);
        return billList;
    }

    /**
     * 记得在Service层调用ProviderDaoImpl实现 供应商名字的设置.
     *
     * @param id
     * @return
     */
    @Override
    public Bill getBillById(String id) {
        Bill bill = null;
        Connection connection = BaseDao.getConnection();
        bill = billDao.getBillById(connection, id);
        String proName = providerDao.getProviderById(connection, bill.getProviderId().toString()).getProName();
        bill.setProviderName(proName);
        BaseDao.closeResources(connection,null,null);
        return bill;
    }

    /**
     * 添加订单bill,开启事务，记得提交
     *
     * @param bill
     * @return
     */
    @Override
    public boolean addBill(Bill bill) {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);
            //这里调用ProviderDao 实现表的获取，但是添加的时候用不了，哎。。。
            String proName = providerDao.getProviderById(connection, "" + bill.getProviderId()).getProName();
            bill.setProviderName(proName);
            flag = billDao.addBill(connection,bill);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 订单修改 bill modify
     *
     * @param bill
     * @return
     */
    @Override
    public boolean modifyBill(Bill bill) {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);
            flag=billDao.modifyBill(connection,bill);
            connection.commit();//记得提交
        } catch (SQLException e) {
            e.printStackTrace();
            //修改失败就回滚
            flag = false;
        }
        return flag;
    }

    /**
     * 点击删除按钮通过id 删除 bill
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteBillById(String id) {
        boolean flag = false;
        Connection connection = BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);
            flag = billDao.deleteBillById(connection,id);
            connection.commit();//提交
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Test // 测试 删除订单 bill
    public void test(){
        System.out.println(deleteBillById("2"));
    }
}
