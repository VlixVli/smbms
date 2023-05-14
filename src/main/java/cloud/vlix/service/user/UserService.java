package cloud.vlix.service.user;

import cloud.vlix.pojo.Role;
import cloud.vlix.pojo.User;
import cloud.vlix.util.Page;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface UserService {
    User getUserByUserCode(String userCode) throws SQLException;
    User login(String userCode, String userPassword) throws SQLException;
    boolean modifyPassword(String userCode, String oldPassword, String newPassword) throws SQLException;
    List<User> getUserList(Page page, String userCode, String roleID) throws SQLException;
    boolean addUser(String userCode, String userName, String userPassword, String gender, String birthday, String phone, String address, String userRole, int createdBy) throws SQLException, ParseException;
    List<Role> getRoleList() throws SQLException;
    User getUserByID(String id) throws SQLException;
    boolean deleteUser(String id) throws SQLException;
    boolean modifyUser(String id, String userName, String gender, String birthday, String phone, String address, String userRole) throws SQLException, ParseException;
}
