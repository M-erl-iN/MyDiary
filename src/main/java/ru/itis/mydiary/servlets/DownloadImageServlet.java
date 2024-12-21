package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.MongoFileRepository;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.FileService;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;


@MultipartConfig
@WebServlet(name="downloadImage", value="/downloadImage")
public class DownloadImageServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = userRepository.findByCookieToken((String) session.getAttribute("cookie_token")).orElse(null);

        String imageId = null;
//        try {
            Part part = req.getPart("file");
            imageId = String.valueOf(fileService.uploadFile(part));
//        } catch (Exception e) {}

        userRepository.setImageId(user.getId(), imageId);
        resp.sendRedirect(req.getContextPath() + "/settings");
    }
}
