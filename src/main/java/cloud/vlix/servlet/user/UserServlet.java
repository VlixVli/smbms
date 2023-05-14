package cloud.vlix.servlet.user;

import cloud.vlix.pojo.Role;
import cloud.vlix.pojo.User;
import cloud.vlix.service.user.UserService;
import cloud.vlix.service.user.UserServiceImpl;
import cloud.vlix.util.Constant;
import cloud.vlix.util.Page;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        User user = (User) req.getSession().getAttribute(Constant.USER);
        String oldPassword = req.getParameter("oldPassword");
        if ("pwdmodify".equals(method)) {
            if (user.getUserPassword().equals(oldPassword)) {
                resp.setContentType("application/json");
                resp.getWriter().write("{\"result\": \"true\"}");
            } else {
                resp.setContentType("application/json");
                resp.getWriter().write("{\"result\": \"false\"}");
            }
        } else if ("query".equals(method)) {
            try {
                String queryName = req.getParameter("queryname");
                String userRole = req.getParameter("queryUserRole");
                String index = req.getParameter("pageIndex");
                Page page = new Page();
                if (index == null) {
                    page.setIndex(1);
                } else {
                    page.setIndex(Integer.parseInt(index));
                }
                page.setPageSize(5);
                List<User> userList = userService.getUserList(page, queryName, userRole);
                List<Role> roleList = userService.getRoleList();
                req.setAttribute("roleList", roleList);
                req.setAttribute("userList", userList);
                req.setAttribute("totalPageCount", page.getTotalPage());
                req.setAttribute("currentPageNo", page.getIndex());
                req.getRequestDispatcher("/jsp/userlist.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("ucexist".equals(method)) {
            try {
                if (userService.getUserByUserCode(req.getParameter("userCode")) == null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"userCode\": \"available\"}");
                } else {
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"userCode\": \"exist\"}");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("getrolelist".equals(method)) {
            try {
                StringBuilder roleList = new StringBuilder();
                roleList.append("[");
                for (Role role : userService.getRoleList()) {
                    roleList.append("{\"id\": \"").append(role.getId()).append("\",").append("\"roleName\": \"").append(role.getRoleName()).append("\" },");
                }
                roleList.deleteCharAt(roleList.length() - 1);
                roleList.append("]");
                resp.setContentType("application/json");
                resp.getWriter().write(roleList.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("view".equals(method)) {
            String userid = req.getParameter("uid");
            try {
                req.setAttribute(Constant.USER, userService.getUserByID(userid));
                req.getRequestDispatcher("/jsp/userview.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("deluser".equals(method)) {
            String uid = req.getParameter("uid");
            try {
                if (userService.deleteUser(uid)) {
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"delResult\": \"true\"}");
                } else {
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"delResult\": \"false\"}");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("modify".equals(method)) {
            String uid = req.getParameter("uid");
            try {
                User userByID = userService.getUserByID(uid);
                req.setAttribute(Constant.USER, userByID);
                req.getRequestDispatcher("/jsp/usermodify.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        User user = (User) req.getSession().getAttribute(Constant.USER);
        String oldPassword = req.getParameter("oldPassword");
        if ("savepwd".equals(method)) {
            String newPassword = req.getParameter("newPassword");
            String renewPassword = req.getParameter("renewPassword");
            if (newPassword != null && newPassword.equals(renewPassword)) {
                if (user.getUserPassword().equals(oldPassword)) {
                    boolean flag = false;
                    try {
                        flag = userService.modifyPassword(user.getUserCode(), oldPassword, newPassword);
                    } catch (SQLException e) {
                        System.out.println("modify password with error = " + e.getMessage());
                    }
                    if (flag) {
                        user.setUserPassword(newPassword);
                        req.getSession().setAttribute(Constant.USER, user);
                        req.setAttribute(Constant.Message, "修改成功！");
                        req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
                    } else {
                        req.setAttribute(Constant.Message, "修改失败！");
                        req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
                    }
                }
            } else {
                req.setAttribute(Constant.Message, "确认密码与新密码不一致！");
                req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
            }
        } else if ("add".equals(method)) {
            String userCode = req.getParameter("userCode");
            String userName = req.getParameter("userName");
            String userPassword = req.getParameter("userPassword");
            String ruserPassword = req.getParameter("ruserPassword");
            String gender = req.getParameter("gender");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            String userRole = req.getParameter("userRole");
            String birthday = req.getParameter("birthday");
            int createdBy = user.getId();
            if (userPassword != null && userPassword.equals(ruserPassword)) {
                boolean addUser;
                try {
                    addUser = userService.addUser(userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy);
                } catch (SQLException e) {
                    addUser = false;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (addUser) {
                    resp.sendRedirect(req.getContextPath() + "/jsp/user?method=query");
                }
            }
        } else if ("modifyexe".equals(method)) {
            String uid = req.getParameter("uid");
            String userName = req.getParameter("userName");
            String gender = req.getParameter("gender");
            String birthday = req.getParameter("birthday");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            String userRole = req.getParameter("userRole");
            try {
                userService.modifyUser(uid, userName, gender, birthday, phone, address, userRole);
            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect(req.getContextPath() + "/jsp/user?method=query");
        }
    }
}
