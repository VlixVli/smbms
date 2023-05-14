package cloud.vlix.service.bill;

import cloud.vlix.pojo.Bill;

import java.sql.SQLException;
import java.util.List;

public interface BillService {
    List<Bill> getBillList(String queryProductName, String queryProviderId, String queryIsPayment) throws SQLException;
    void addBill(String billCode, String productName, String productUnit, String productCount, String totalPrice, String providerId, String isPayment) throws SQLException;
    boolean delBill(String id) throws SQLException;
    Bill getBillById(String id) throws SQLException;
}
