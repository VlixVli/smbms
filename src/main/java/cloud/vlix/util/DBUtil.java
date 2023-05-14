package cloud.vlix.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static final String url;
    private static final String userName;
    private static final String password;

    static {
        String dbPropertiesUrl = "db.properties";
        Properties dbProperties = new Properties();
        InputStream is = DBUtil.class.getClassLoader().getResourceAsStream(dbPropertiesUrl);
        try {
            dbProperties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String driver = dbProperties.getProperty(Constant.DB_DRIVER);
        url = dbProperties.getProperty(Constant.DB_URL);
        userName = dbProperties.getProperty(Constant.DB_USERNAME);
        password = dbProperties.getProperty(Constant.DB_PASSWORD);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public static ResultSet executeQuery(PreparedStatement preparedStatement, Object [] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    public static int executeUpdate(PreparedStatement preparedStatement, Object [] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    public static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
