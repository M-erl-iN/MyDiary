package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.dto.CreateNotebookDto;
import ru.itis.mydiary.dto.CreateNotebookResponse;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.impl.NotebookRepositoryImpl;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.NotebookService;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name="createDiary", value="/createDiary")
public class CreateDiaryServlet extends HttpServlet {
    private UserRepositoryImpl userRepository;
    private NotebookService notebookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userRepository = (UserRepositoryImpl) context.getAttribute("userRepository");
        notebookService = (NotebookService) context.getAttribute("notebookService");
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateNotebookDto notebookDto = CreateNotebookDto.builder()
                .title(req.getParameter("title"))
                .description(req.getParameter("description"))
                .build();

        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);
        CreateNotebookResponse createResponse = notebookService.createNotebook(notebookDto, userOptional.get().getId());

        if (createResponse.getStatus() == 0) {
            resp.sendRedirect(req.getContextPath() + "/profile");
        }
    }
}
