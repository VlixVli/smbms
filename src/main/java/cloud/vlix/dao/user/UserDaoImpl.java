package cloud.vlix.dao.user;

import cloud.vlix.pojo.Role;
import cloud.vlix.pojo.User;
import cloud.vlix.util.DBUtil;
import cloud.vlix.util.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUserByUserCode(String userCode) throws SQLException {
        User user = null;
        String sql = "select * from smbms_user where userCode = ?";
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        Object [] params = { userCode };
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getString("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getString("modifyDate"));
        }
        DBUtil.closeResource(connection, preparedStatement, resultSet);
        return user;
    }

    @Override
    public boolean modifyPassword(String userCode, String userPassword) throws SQLException {
        String sql = "update smbms_user set userPassword = ? where userCode = ?";
        Object [] params = { userPassword, userCode };
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int updateRaws = DBUtil.executeUpdate(preparedStatement, params);
        if (updateRaws > 0) {
            return true;
        }
        DBUtil.closeResource(connection, preparedStatement, null);
        return false;
    }

    @Override
    public List<User> getUserList(Page page, String userCode, Integer roleID) throws SQLException {
        StringBuilder sql = new StringBuilder("select count(id) from smbms_user where 1 = 1");
        Object [] params;
        ArrayList<Object> objects = new ArrayList<>();
        if (userCode != null) {
            sql.append(" and userCode = ?");
            objects.add(userCode);
        }
        if (roleID != null) {
            sql.append(" and userRole = ?");
            objects.add(roleID);
        }
        params = objects.toArray();
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        if (resultSet.next()) {
            page.setTotalPage((resultSet.getInt("count(id)") + page.getPageSize() - 1) / page.getPageSize());
        }
        sql.setLength(0);
        sql.append("select * from smbms_user u, smbms_role r where u.userRole = r.id");
        if (userCode != null) {
            sql.append(" and userCode = ?");
        }
        if (roleID != null) {
            sql.append(" and userRole = ?");
        }
        sql.append(" limit ?, ?");
        objects.add(page.getPageSize() * (page.getIndex() - 1));
        objects.add(page.getPageSize());
        params = objects.toArray();
        List<User> userList = new ArrayList<>();
        User user;
        preparedStatement = connection.prepareStatement(sql.toString());
        resultSet = DBUtil.executeQuery(preparedStatement, params);
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setAge();
            user.setUserRoleName(resultSet.getString("roleName"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getString("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getString("modifyDate"));
            userList.add(user);
        }
        DBUtil.closeResource(connection, preparedStatement, resultSet);
        return userList;
    }

    @Override
    public boolean addUser(String userCode, String userName, String userPassword, int gender, Date birthday, String phone, String address, int userRole, int createdBy) throws SQLException {
        String sql = "insert into smbms_user(userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        Object [] params = { userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, new Date() };
        boolean flag = DBUtil.executeUpdate(preparedStatement, params) > 0;
        DBUtil.closeResource(connection, preparedStatement, null);
        return flag;
    }

    @Override
    public List<Role> getRoleList() throws SQLException {
        String sql = "select * from smbms_role";
        Role role;
        Object [] params = {};
        List<Role> roles = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        while (resultSet.next()) {
            role = new Role();
            role.setId(resultSet.getInt("id"));
            role.setRoleCode(resultSet.getString("roleCode"));
            role.setRoleName(resultSet.getString("roleName"));
            role.setCreatedBy(resultSet.getInt("createdBy"));
            role.setCreationDate(resultSet.getString("creationDate"));
            role.setModifyBy(resultSet.getInt("modifyBy"));
            role.setModifyDate(resultSet.getString("modifyDate"));
            roles.add(role);
        }
        DBUtil.closeResource(connection, preparedStatement ,resultSet);
        return roles;
    }

    @Override
    public User getUserByUserID(int id) throws SQLException {
        User user = null;
        String sql = "select * from smbms_user u, smbms_role r where u.userRole = r.id and u.id = ?";
        Object [] params = {id};
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = DBUtil.executeQuery(preparedStatement, params);
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setAge();
            user.setUserRoleName(resultSet.getString("roleName"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getString("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getString("modifyDate"));
        }
        DBUtil.closeResource(connection, preparedStatement, resultSet);
        return user;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String sql = "delete from smbms_user where id = ?";
        Object [] params = {id};
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int updateRaws = DBUtil.executeUpdate(preparedStatement, params);
        boolean flag = updateRaws > 0;
        DBUtil.closeResource(connection, preparedStatement, null);
        return flag;
    }

    @Override
    public boolean modifyUser(int id, String userName, int gender, Date birthday, String phone, String address, int userRole) throws SQLException {
        String sql = "update smbms_user set userName = ?, gender = ?, birthday = ?, phone = ?, address = ?, userRole = ? where id = ?";
        Object [] params = {userName, gender, birthday, phone, address, userRole, id};
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int updateRaws = DBUtil.executeUpdate(preparedStatement, params);
        boolean flag = updateRaws > 0;
        DBUtil.closeResource(connection, preparedStatement, null);
        return flag;
    }
}
