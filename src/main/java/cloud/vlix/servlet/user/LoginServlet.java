package cloud.vlix.servlet.user;

import cloud.vlix.pojo.User;
import cloud.vlix.service.user.UserService;
import cloud.vlix.service.user.UserServiceImpl;
import cloud.vlix.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(Constant.USER);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/jsp/frame.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        UserService userService = new UserServiceImpl();
        User user = null;
        try {
            user = userService.login(userCode, userPassword);
        } catch (SQLException e) {
            System.out.println("login with error = " + e.getMessage());
        }
        if (user == null) {
            req.setAttribute("error", "password incorrect or user not exists!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute(Constant.USER, user);
            req.setAttribute(Constant.USER, user);
            req.getRequestDispatcher("/jsp/frame.jsp").forward(req, resp);
        }
    }
}
