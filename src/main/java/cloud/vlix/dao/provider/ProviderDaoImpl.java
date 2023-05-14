package cloud.vlix.dao.provider;

import cloud.vlix.pojo.Provider;
import cloud.vlix.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl implements ProviderDao {

    @Override
    public List<Provider> getProviderList() throws SQLException {
        String sql = "select * from smbms_provider";
        Object [] params = {};
        ArrayList<Provider> providerList = new ArrayList<>();
        Provider provider;
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        while (resultSet.next()) {
            provider = new Provider();
            provider.setId(resultSet.getInt("id"));
            provider.setProCode(resultSet.getString("proCode"));
            provider.setProName(resultSet.getString("proName"));
            provider.setProDesc(resultSet.getString("proDesc"));
            provider.setProContact(resultSet.getString("proContact"));
            provider.setProPhone(resultSet.getString("proPhone"));
            provider.setProAddress(resultSet.getString("proAddress"));
            provider.setProFax(resultSet.getString("proFax"));
            provider.setCreatedBy(resultSet.getInt("createdBy"));
            provider.setCreationDate(resultSet.getDate("creationDate"));
            provider.setModifyBy(resultSet.getInt("modifyBy"));
            provider.setModifyDate(resultSet.getDate("modifyDate"));
            providerList.add(provider);
        }
        DBUtil.closeResource(connection, preparedStatement, resultSet);
        return providerList;
    }
}
