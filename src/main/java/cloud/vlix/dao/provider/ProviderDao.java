package cloud.vlix.dao.provider;

import cloud.vlix.pojo.Provider;

import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {
    List<Provider> getProviderList() throws SQLException;
}
