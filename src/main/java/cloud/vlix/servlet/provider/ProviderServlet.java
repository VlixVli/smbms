package cloud.vlix.servlet.provider;

import cloud.vlix.pojo.Provider;
import cloud.vlix.service.provider.ProviderService;
import cloud.vlix.service.provider.ProviderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProviderServlet extends HttpServlet {
    private final ProviderService providerService = new ProviderServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("query".equals(method)) {
            try {
                List<Provider> providerList = providerService.getProviderList();
                req.setAttribute("providerList", providerList);
                req.getRequestDispatcher("/jsp/providerlist.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
