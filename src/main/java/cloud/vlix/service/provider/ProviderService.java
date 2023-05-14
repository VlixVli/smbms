package cloud.vlix.service.provider;

import cloud.vlix.pojo.Provider;

import java.sql.SQLException;
import java.util.List;

public interface ProviderService {
    List<Provider> getProviderList() throws SQLException;
}
