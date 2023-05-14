package cloud.vlix.service.user;

import cloud.vlix.dao.user.UserDao;
import cloud.vlix.dao.user.UserDaoImpl;
import cloud.vlix.pojo.Role;
import cloud.vlix.pojo.User;
import cloud.vlix.util.Page;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User getUserByUserCode(String userCode) throws SQLException {
        return userDao.getUserByUserCode(userCode);
    }

    @Override
    public User login(String userCode, String userPassword) throws SQLException {
        User user = getUserByUserCode(userCode);
        if (userPassword != null && user != null && userPassword.equals(user.getUserPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean modifyPassword(String userCode, String oldPassword, String newPassword) throws SQLException {
        User user = login(userCode, oldPassword);
        if (user != null) {
            return  userDao.modifyPassword(userCode, newPassword);
        }
        return false;
    }

    @Override
    public List<User> getUserList(Page page, String userCode, String roleID) throws SQLException {
        Integer role = null;
        if ("".equals(userCode) || userCode == null) {
            userCode = null;
        }
        if (!"".equals(roleID) && roleID != null && !"0".equals(roleID)) {
            role = Integer.valueOf(roleID);
        }
        return userDao.getUserList(page, userCode ,role);
    }

    @Override
    public boolean addUser(String userCode, String userName, String userPassword, String gender, String birthday, String phone, String address, String userRole, int createdBy) throws SQLException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date userBirthday = simpleDateFormat.parse(birthday);
        int userGender = Integer.parseInt(gender);
        int role = Integer.parseInt(userRole);
        return userDao.addUser(userCode, userName, userPassword, userGender, userBirthday, phone, address, role, createdBy);
    }

    @Override
    public List<Role> getRoleList() throws SQLException {
        return userDao.getRoleList();
    }

    @Override
    public User getUserByID(String id) throws SQLException {
        int userID = Integer.parseInt(id);
        return userDao.getUserByUserID(userID);
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        int userID = Integer.parseInt(id);
        return userDao.deleteUser(userID);
    }

    @Override
    public boolean modifyUser(String id, String userName, String gender, String birthday, String phone, String address, String userRole) throws SQLException, ParseException {
        int userID = Integer.parseInt(id);
        int userGender = Integer.parseInt(gender);
        int role = Integer.parseInt(userRole);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date userBirthday = simpleDateFormat.parse(birthday);
        return userDao.modifyUser(userID, userName, userGender, userBirthday, phone, address, role);
    }
}
