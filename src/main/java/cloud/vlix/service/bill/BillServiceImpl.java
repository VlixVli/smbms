package cloud.vlix.service.bill;

import cloud.vlix.dao.bill.BillDao;
import cloud.vlix.dao.bill.BillDaoImpl;
import cloud.vlix.pojo.Bill;

import java.sql.SQLException;
import java.util.List;

public class BillServiceImpl implements BillService {
    private final BillDao billDao = new BillDaoImpl();
    @Override
    public List<Bill> getBillList(String queryProductName, String queryProviderId, String queryIsPayment) throws SQLException {
        String productName = null;
        Integer providerId = null;
        Integer isPayment = null;
        if (!"".equals(queryProductName) && queryProductName != null) {
            productName = queryProductName;
        }
        if (!"".equals(queryProviderId) && queryProviderId != null && !"0".equals(queryProviderId)){
            providerId = Integer.valueOf(queryProviderId);
        }
        if (!"".equals(queryIsPayment) && queryIsPayment != null && !"0".equals(queryIsPayment)){
            providerId = Integer.valueOf(queryIsPayment);
        }
        return billDao.getBillList(productName, providerId, isPayment);
    }

    @Override
    public void addBill(String billCode, String productName, String productUnit, String productCount, String totalPrice, String providerId, String isPayment) throws SQLException {
        float productCountFormat = Float.parseFloat(productCount);
        float totalPriceFormat = Float.parseFloat(totalPrice);
        int providerIdFormat = Integer.parseInt(providerId);
        billDao.addBill(billCode, productName, productUnit, productCountFormat, totalPriceFormat, providerIdFormat, isPayment);
    }

    @Override
    public boolean delBill(String id) throws SQLException {
        int billId = Integer.parseInt(id);
        return billDao.delBill(billId);
    }

    @Override
    public Bill getBillById(String id) throws SQLException {
        int billId = Integer.parseInt(id);
        return billDao.getBillById(billId);
    }
}
