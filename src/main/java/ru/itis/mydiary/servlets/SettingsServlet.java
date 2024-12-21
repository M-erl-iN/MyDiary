package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.mydiary.dto.UserUpdateDto;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.MongoFileRepository;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.FileService;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;


@MultipartConfig
@WebServlet(name="settings", value="/settings")
public class SettingsServlet extends HttpServlet {
    private UserServiceImpl userService;
    private UserRepositoryImpl userRepository;
    private MongoFileRepository mongoFileRepository;
    private FileService fileService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepositoryImpl) config.getServletContext().getAttribute("userRepository");
        mongoFileRepository = (MongoFileRepository) config.getServletContext().getAttribute("mongoFileRepository");
        fileService = (FileService) config.getServletContext().getAttribute("fileService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = userRepository.findByCookieToken((String) session.getAttribute("cookie_token")).orElse(null);

        if (user == null) {resp.sendRedirect(req.getContextPath() + "/signIn"); return;}


        req.setAttribute("user", user);
//        fileService.downloadFile(UUID.fromString(user.getImageId()), resp);
//        req.setAttribute("imageOS", fileService.); записано прямо в респонс
        req.getRequestDispatcher("/jsp/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = userRepository.findByCookieToken((String) session.getAttribute("cookie_token")).orElse(null);

        if (user == null) {resp.sendRedirect(req.getContextPath() + "/signIn"); return;}

        userService.update(user, req);
        resp.sendRedirect(req.getContextPath() + "/settings");
    }
}
