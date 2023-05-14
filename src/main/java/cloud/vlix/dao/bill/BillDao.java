package cloud.vlix.dao.bill;

import cloud.vlix.pojo.Bill;

import java.sql.SQLException;
import java.util.List;

public interface BillDao {
    List<Bill> getBillList(String queryProductName, Integer queryProviderId, Integer isPayment) throws SQLException;

    boolean addBill(String billCode, String productName, String productUnit, Float productCount, Float totalPrice, int providerId, String isPayment) throws SQLException;
    boolean delBill(int id) throws SQLException;
    Bill getBillById(int id) throws SQLException;
}
