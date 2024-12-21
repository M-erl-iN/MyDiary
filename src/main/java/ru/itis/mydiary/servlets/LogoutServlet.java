package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.mydiary.service.impl.UserServiceImpl;
import ru.itis.mydiary.util.AuthUtils;

import java.io.IOException;


@WebServlet(name="logout", value="/logout")
public class LogoutServlet extends HttpServlet {
    UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthUtils.clearSession(req);
        AuthUtils.clearCookies(req, resp);
        resp.sendRedirect(req.getContextPath() + "/signIn");
    }
}
