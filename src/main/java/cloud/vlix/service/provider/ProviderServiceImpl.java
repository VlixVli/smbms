package cloud.vlix.service.provider;

import cloud.vlix.dao.provider.ProviderDao;
import cloud.vlix.dao.provider.ProviderDaoImpl;
import cloud.vlix.pojo.Provider;

import java.sql.SQLException;
import java.util.List;

public class ProviderServiceImpl implements ProviderService {
    private final ProviderDao providerDao = new ProviderDaoImpl();
    @Override
    public List<Provider> getProviderList() throws SQLException {
        return providerDao.getProviderList();
    }
}
