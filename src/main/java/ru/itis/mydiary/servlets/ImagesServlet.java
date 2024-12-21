package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.mydiary.service.FileService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name="images", value="/images")
public class ImagesServlet extends HttpServlet {
    private FileService fileService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        fileService = (FileService) config.getServletContext().getAttribute("fileService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileService.downloadFile(req.getParameter("imId"), resp);
    }
}
