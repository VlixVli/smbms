package cloud.vlix.servlet.bill;

import cloud.vlix.pojo.Bill;
import cloud.vlix.pojo.Provider;
import cloud.vlix.service.bill.BillService;
import cloud.vlix.service.bill.BillServiceImpl;
import cloud.vlix.service.provider.ProviderService;
import cloud.vlix.service.provider.ProviderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BillServlet extends HttpServlet {
    private final BillService billService = new BillServiceImpl();
    private final ProviderService providerService = new ProviderServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("query".equals(method)) {
            String queryProductName = req.getParameter("queryProductName");
            String queryProviderId = req.getParameter("queryProviderId");
            String isPayment = req.getParameter("queryIsPayment");
            try {
                List<Bill> billList = billService.getBillList(queryProductName, queryProviderId, isPayment);
                List<Provider> providerList = providerService.getProviderList();
                req.setAttribute("providerList", providerList);
                req.setAttribute("billList", billList);
                req.getRequestDispatcher("/jsp/billlist.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("getproviderlist".equals(method)) {
            try {
                resp.setContentType("application/json");
                StringBuilder providerList = new StringBuilder();
                providerList.append("[");
                for (Provider provider : providerService.getProviderList()) {
                    providerList.append("{\"id\": \"").append(provider.getId()).append("\", \"proName\": \"").append(provider.getProName()).append("\"},");
                }
                providerList.deleteCharAt(providerList.length() - 1);
                providerList.append("]");
                resp.getWriter().write(providerList.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("delbill".equals(method)) {
            String billid = req.getParameter("billid");
            try {
                boolean delBill = billService.delBill(billid);
                resp.setContentType("application/json");
                if (delBill) {
                    resp.getWriter().write("{\"delResult\": \"true\"}");
                } else {
                    resp.getWriter().write("{\"delResult\": \"false\"}");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("view".equals(method)) {
            String billid = req.getParameter("billid");
            try {
                Bill billById = billService.getBillById(billid);
                req.setAttribute("bill", billById);
                req.getRequestDispatcher("/jsp/billview.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getParameter("method");
        if ("add".equals(method)) {
            String billCode = req.getParameter("billCode");
            String productName = req.getParameter("productName");
            String productUnit = req.getParameter("productUnit");
            String productCount = req.getParameter("productCount");
            String totalPrice = req.getParameter("totalPrice");
            String providerId = req.getParameter("providerId");
            String isPayment = req.getParameter("isPayment");
            try {
                billService.addBill(billCode, productName, productUnit, productCount, totalPrice, providerId, isPayment);
                resp.sendRedirect(req.getContextPath() + "/jsp/bill?method=query");
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        } else if ("modify".equals(method)) {
            String id = req.getParameter("id");
            String billCode = req.getParameter("billCode");
            String productName = req.getParameter("productName");
            String productUnit = req.getParameter("productUnit");
            String productCount = req.getParameter("productCount");
            String totalPrice = req.getParameter("totalPrice");
            String providerId = req.getParameter("providerId");
            String isPayment = req.getParameter("isPayment");

        }
    }
}
