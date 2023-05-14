package cloud.vlix.dao.user;

import cloud.vlix.pojo.Role;
import cloud.vlix.pojo.User;
import cloud.vlix.util.Page;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface UserDao {
    User getUserByUserCode(String userCode) throws SQLException;
    boolean modifyPassword(String userCode, String userPassword) throws SQLException;
    List<User> getUserList(Page page, String userCode, Integer roleID) throws SQLException;
    boolean addUser(String userCode, String userName, String userPassword, int gender, Date birthday, String phone, String address, int userRole, int createdBy) throws SQLException;
    List<Role> getRoleList() throws SQLException;
    User getUserByUserID(int id) throws SQLException;
    boolean deleteUser(int id) throws SQLException;
    boolean modifyUser(int id, String userName, int gender, Date birthday, String phone, String address, int userRole) throws SQLException;
}
