package cloud.vlix.dao.bill;

import cloud.vlix.pojo.Bill;
import cloud.vlix.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao {

    @Override
    public List<Bill> getBillList(String productName, Integer providerId, Integer isPayment) throws SQLException {
        StringBuilder sql = new StringBuilder("select * from smbms_bill b, smbms_provider p where b.providerId = p.id");
        Object [] params;
        ArrayList<Object> objects = new ArrayList<>();
        if (productName != null) {
            sql.append(" and b.productName = ?");
            objects.add(productName);
        }
        if (providerId != null) {
            sql.append(" and b.providerId = ?");
            objects.add(providerId);
        }
        if (isPayment != null) {
            sql.append(" and b.isPayment = ?");
            objects.add(isPayment);
        }
        params = objects.toArray();
        System.out.println(sql.toString());
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        Bill bill;
        List<Bill> billList = new ArrayList<>();
        while (resultSet.next()) {
            bill = new Bill();
            bill.setId(resultSet.getInt("id"));
            bill.setBillCode(resultSet.getString("billCode"));
            bill.setProductName(resultSet.getString("productName"));
            bill.setProductDesc(resultSet.getString("productDesc"));
            bill.setProductUnit(resultSet.getString("productUnit"));
            bill.setProductCount(resultSet.getString("productCount"));
            bill.setTotalPrice(resultSet.getString("totalPrice"));
            bill.setIsPayment(resultSet.getInt("isPayment"));
            bill.setCreatedBy(resultSet.getInt("createdBy"));
            bill.setCreationDate(resultSet.getDate("creationDate"));
            bill.setModifyBy(resultSet.getInt("modifyBy"));
            bill.setModifyDate(resultSet.getDate("modifyDate"));
            bill.setProviderId(resultSet.getInt("providerId"));
            bill.setProviderName(resultSet.getString("proName"));
            billList.add(bill);
        }
        DBUtil.closeResource(connection, preparedStatement, resultSet);
        return billList;
    }

    @Override
    public boolean addBill(String billCode, String productName, String productUnit, Float productCount, Float totalPrice, int providerId, String isPayment) throws SQLException {
        String sql = "insert into smbms_bill(billCode, productName, productUnit, productCount, totalPrice, providerId, isPayment) value(?, ?, ?, ?, ?, ?, ?)";
        Object [] params = {billCode, productName, productUnit, productCount, totalPrice, providerId, isPayment};
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int update = DBUtil.executeUpdate(preparedStatement, params);
        DBUtil.closeResource(connection, preparedStatement, null);
        return update > 0;
    }

    @Override
    public boolean delBill(int id) throws SQLException {
        String sql = "delete from smbms_bill where id = ?";
        Object [] params = {id};
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int update = DBUtil.executeUpdate(preparedStatement, params);
        return update > 0;
    }

    @Override
    public Bill getBillById(int id) throws SQLException {
        String sql = "select * from smbms_bill b, smbms_provider p where b.providerId = p.id and b.id = ?";
        Object [] params = {id};
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        Bill bill = null;
        if (resultSet.next()) {
            bill = new Bill();
            bill.setId(resultSet.getInt("id"));
            bill.setBillCode(resultSet.getString("billCode"));
            bill.setProductName(resultSet.getString("productName"));
            bill.setProductDesc(resultSet.getString("productDesc"));
            bill.setProductUnit(resultSet.getString("productUnit"));
            bill.setProductCount(resultSet.getString("productCount"));
            bill.setTotalPrice(resultSet.getString("totalPrice"));
            bill.setIsPayment(resultSet.getInt("isPayment"));
            bill.setCreatedBy(resultSet.getInt("createdBy"));
            bill.setCreationDate(resultSet.getDate("creationDate"));
            bill.setModifyBy(resultSet.getInt("modifyBy"));
            bill.setModifyDate(resultSet.getDate("modifyDate"));
            bill.setProviderId(resultSet.getInt("providerId"));
            bill.setProviderName(resultSet.getString("proName"));
        }
        DBUtil.closeResource(connection, preparedStatement, resultSet);
        return bill;
    }
}
