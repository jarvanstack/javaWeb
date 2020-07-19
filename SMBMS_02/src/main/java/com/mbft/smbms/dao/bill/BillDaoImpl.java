package com.mbft.smbms.dao.bill;

import com.mbft.smbms.dao.BaseDao;
import com.mbft.smbms.pojo.Bill;
import com.mbft.smbms.util.StringUtil01;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao{
    /**
     * 获取bill list 集合，
     * bill 是前端的查询信息的参数封装
     *
     * @param connection
     * @param bill
     * @return
     */
    @Override
    public List<Bill> getBillList(Connection connection, Bill bill) {
        List<Bill> billList = new ArrayList<>();
        List<String> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String sqlBase  = "SELECT\n" +
                "\tsmbms.smbms_bill.*,\n" +
                "\tsmbms.smbms_provider.proName\n" +
                "FROM\n" +
                "\tsmbms.smbms_bill,\n" +
                "\tsmbms.smbms_provider\n" +
                "WHERE\n" +
                "\tsmbms_bill.providerId = smbms_provider.id ";
        sql.append(sqlBase);
        if (StringUtil01.isNotNull(bill.getProductName())){
            //如果查询产品名不为空,就增加条件查询
            sql.append(" AND smbms_provider.proName LIKE ? ");
            params.add("%"+bill.getProductName()+"%");
        }
        if (StringUtil01.isNotNull(bill.getProviderId().toString())) {
            //如果供应商的id 不为空，就 增加查询条件
            sql.append(" \tAND smbms_bill.providerId = ? \n ");
            params.add(bill.getProviderId().toString());
        }
        if (StringUtil01.isNotNull(bill.getIsPayment().toString())){
            //如果支付不为空，支付条件查询
            sql.append(" \tAND smbms_bill.isPayment = ? ");
            params.add(bill.getIsPayment().toString());
        }
        try {
            preparedStatement = connection.prepareStatement(sqlBase);
            resultSet = BaseDao.executeQuery(preparedStatement,params.toArray());
            while (resultSet.next()) {
                Bill _bill = new Bill();
                _bill.setId(resultSet.getInt("id"));
                _bill.setBillCode(resultSet.getString("billCode"));
                _bill.setProductName(resultSet.getString("productName"));
                _bill.setProductDesc(resultSet.getString("productDesc"));
                _bill.setProductUnit(resultSet.getString("productUnit"));
                _bill.setProductCount(resultSet.getBigDecimal("productCount"));
                _bill.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                _bill.setIsPayment(resultSet.getInt("isPayment"));
                _bill.setProviderId(resultSet.getInt("providerId"));
                _bill.setCreatedBy(resultSet.getInt("createdBy"));
                _bill.setCreationDate(resultSet.getDate("creationDate"));
                _bill.setModifyBy(resultSet.getInt("modifyBy"));
                _bill.setModifyDate(resultSet.getDate("modifyDate"));
                billList.add(_bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResources(connection,preparedStatement,resultSet);
        }
        return billList;
    }
    @Test // 测试获取 getBillList（）
    public void testgetBillList(){
        Bill bill = new Bill();
        List<Bill> billList = getBillList(BaseDao.getConnection(), bill);
        for (Bill bill1 : billList) {
            System.out.println(bill1.getId());
        }
    }
    /**
     * 添加订单bill
     *
     * @param connection
     * @param bill
     * @return
     */
    @Override
    public boolean addBill(Connection connection, Bill bill) throws SQLException {
        boolean flag = false;
        if (connection!=null) {
            PreparedStatement preparedStatement = null;
            ArrayList<Object> params = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            String baseSql = "INSERT INTO `smbms`.`smbms_bill` ( `billCode`, `productName`, `productDesc`, `productUnit`, `productCount`,`totalPrice`, `isPayment`, `createdBy`, `creationDate`, `modifyBy`, `modifyDate`, `providerId` )\n" +
                    "VALUES\n" +
                    "\t(\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "?,"+
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t\t?,\n" +
                    "\t?,\n" +
                    "\t ? )";// 12 因为id是自动生成的不能改。
            params.add(bill.getBillCode());
            params.add(bill.getProductName());
            params.add(bill.getProductDesc());
            params.add(bill.getProductUnit());
            params.add(bill.getProductCount());
            params.add(bill.getTotalPrice());
            params.add(bill.getIsPayment());
            params.add(bill.getCreatedBy());
            params.add(bill.getCreationDate());
            params.add(bill.getModifyBy());
            params.add(bill.getModifyDate());
            params.add(bill.getProviderId());
//            params.add(bill.getProviderName());//12 这个需要去查出来通过表,这个表里面没有的你会添加失败的，
            sql.append(baseSql);
            preparedStatement = connection.prepareStatement(sql.toString());
            int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
            if (i > 0) {
                flag = true;
            }

        }
        return flag;
    }
    @Test // 测试添加订单 addBill  成功
    public void testAddBill() throws SQLException {
        Bill billById = getBillById(BaseDao.getConnection(), "3");
        billById.setProductName("李亚洲");
        System.out.println(addBill(BaseDao.getConnection(), billById));
    }
    /**
     * 通过 id 获取 bill，订单，
     *
     * @param connection
     * @param id
     * @return
     */
    @Override
    public Bill getBillById(Connection connection, String id) {
        Bill bill = new Bill();
        if (connection!=null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> params = new ArrayList<>();
            String basesql = "SELECT * FROM `smbms`.`smbms_bill` where id = ? ";
            sql.append(basesql);
            params.add(id);
            try {
                preparedStatement = connection.prepareStatement(sql.toString());
                resultSet = BaseDao.executeQuery(preparedStatement,params.toArray());
                while (resultSet.next()) {
                    bill.setId(resultSet.getInt("id"));
                    bill.setBillCode(resultSet.getString("billCode"));
                    bill.setProductName(resultSet.getString("productName"));
                    bill.setProductDesc(resultSet.getString("productDesc"));
                    bill.setProductUnit(resultSet.getString("productUnit"));
                    bill.setProductCount(resultSet.getBigDecimal("productCount"));
                    bill.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                    bill.setIsPayment(resultSet.getInt("isPayment"));
                    bill.setCreatedBy(resultSet.getInt("createdBy"));
                    bill.setCreationDate(resultSet.getDate("creationDate"));
                    bill.setModifyBy(resultSet.getInt("modifyBy"));
                    bill.setModifyDate(resultSet.getDate("modifyDate"));
                    bill.setProviderId(resultSet.getInt("providerId"));
//                    bill.setId(resultSet.getInt(""));//providerName你是获取不到的因为在Provider表里面，

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return bill;
    }

    /**
     * 订单修改 bill modify
     *
     * @param connection
     * @param bill
     * @return
     */
    @Override
    public boolean modifyBill(Connection connection, Bill bill) throws SQLException {
        boolean flag = false;
        if(connection!=null) {
            PreparedStatement preparedStatement = null;
            ArrayList<Object> params = new ArrayList<>();
            String baseSql = "UPDATE `smbms`.`smbms_bill` \n" +
                    "SET `billCode` = ?,\n" +
                    "`productName` = ?,\n" +
                    "`productDesc` = ?,\n" +
                    "`productUnit` = ?,\n" +
                    "`productCount` = ? ,\n" +
                    "`totalPrice` = ?,\n" +
                    "`isPayment` = ?,\n" +
                    "`createdBy` = ?,\n" +
                    "`creationDate` = ?,\n" +
                    "`modifyBy` = ?,\n" +
                    "`modifyDate` = ?,\n" +
                    "`providerId` = ? \n" + // 12
                    "WHERE\n" +
                    "\t`id` = ?"; // 13
            params.add(bill.getBillCode());
            params.add(bill.getProductName());
            params.add(bill.getProductDesc());
            params.add(bill.getProductUnit());
            params.add(bill.getProductCount());
            params.add(bill.getTotalPrice());
            params.add(bill.getIsPayment());
            params.add(bill.getCreatedBy());
            params.add(bill.getCreationDate());
            params.add(bill.getModifyBy());
            params.add(bill.getModifyDate());
            params.add(bill.getProviderId());  // 12
            params.add(bill.getId());// 13 修改位置的id
            //抛出异常service开启事务
            preparedStatement = connection.prepareStatement(baseSql);
            int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
            if (i > 0) {
                flag = true;
            }
            BaseDao.closeResources(connection, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 点击删除按钮通过id 删除 bill
     *
     * @param connection
     * @param id
     * @return
     */
    @Override
    public boolean deleteBillById(Connection connection, String id) throws SQLException {
        boolean flag = false;
        String baseSql = "DELETE FROM `smbms`.`smbms_bill` WHERE `id` = ?";
        PreparedStatement preparedStatement = null;
        ArrayList<Object> params = new ArrayList<>();

        //抛出异常，给service事务，记得提交
        preparedStatement = connection.prepareStatement(baseSql);
        params.add(id);
        int i = BaseDao.executeUpdate(preparedStatement, params.toArray());
        if (i > 0) {
            flag = true;
        }
        BaseDao.closeResources(connection,preparedStatement,null);
        return flag;
    }

    @Test // 点击删除按钮通过id 删除 bill
    public void testmodifyBill(){
        try {
            System.out.println(deleteBillById(BaseDao.getConnection(), "3"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
