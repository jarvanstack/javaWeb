package com.mbft.smbms.dao.bill;

import com.mbft.smbms.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BillDao {
    /**
     * 获取bill list 集合，
     * bill 是前端的查询信息的参数封装
     * @param bill
     * @return
     */
    List<Bill> getBillList(Connection connection,Bill bill);

    /**
     *  添加订单bill
     * @param connection
     * @param bill
     * @return
     */
    boolean addBill(Connection connection ,Bill bill) throws SQLException;

    /**
     * 通过 id 获取 bill，订单，
     * @param connection
     * @param id
     * @return
     */
    Bill getBillById(Connection connection ,String id);

    /**
     * 订单修改 bill modify
     * @param connection
     * @param bill
     * @return
     */
    boolean modifyBill(Connection connection , Bill bill) throws SQLException;

    /**
     * 点击删除按钮通过id 删除 bill
     * @param connection
     * @param id
     * @return
     */
    boolean deleteBillById(Connection connection ,String id) throws SQLException;
}
