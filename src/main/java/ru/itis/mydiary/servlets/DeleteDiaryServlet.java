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
import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.RoleInteractionRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.NotebookService;
import ru.itis.mydiary.service.UserService;
import ru.itis.mydiary.util.AuthUtils;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name="deleteDiary", value="/deleteDiary")
public class DeleteDiaryServlet extends HttpServlet {
    private UserRepository userRepository;
    private NotebookRepository notebookRepository;
    private UserService userService;
    private NotebookService notebookService;
    private RoleInteractionRepository roleInteractionRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userRepository = (UserRepositoryImpl) context.getAttribute("userRepository");
        notebookRepository = (NotebookRepository) context.getAttribute("notebookRepository");
        userService = (UserService) context.getAttribute("userService");
        notebookService = (NotebookService) context.getAttribute("notebookService");
        roleInteractionRepository = (RoleInteractionRepository) context.getAttribute("roleInteractionRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);
//        if (AuthUtils.checkEntity(userOptional, req, resp, "incorrect user cookie")) {
//            return;}

        Long notebookId = Long.parseLong(req.getParameter("nId"));
        Optional<Notebook> notebookOptional = notebookRepository.findById(notebookId);
//        if (AuthUtils.checkEntity(notebookOptional, req, resp, "incorrect notebook id")) {
//            return;}

        User user = userOptional.get();
        Notebook notebook = notebookOptional.get();
        boolean deleteType = user.getId() == notebook.getCreatorId();
        if (deleteType) {
            roleInteractionRepository.deleteByUserIdAndNotebookId(user.getId(), notebookId);
            notebookRepository.deleteById(notebookId);
        } else {
            roleInteractionRepository.deleteByUserIdAndNotebookId(user.getId(), notebookId);
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
