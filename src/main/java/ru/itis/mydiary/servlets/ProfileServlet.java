package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
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
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.NotebookService;
import ru.itis.mydiary.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name="profile", value="/profile")
public class ProfileServlet extends HttpServlet {
    UserService userService;
    UserRepository userRepository;

    NotebookService notebookService;
    NotebookRepository notebookRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepository) config.getServletContext().getAttribute("userRepository");
        notebookService = (NotebookService) config.getServletContext().getAttribute("notebookService");
        notebookRepository = (NotebookRepository) config.getServletContext().getAttribute("notebookRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);

        long start = 0l;
        int step = 6;
        if (req.getParameter("start") != null) {
            start = Long.parseLong(req.getParameter("start"));
            if (start < 0) {
                start = 0;
            }
        }

//        List<Notebook> notebooks = userRepository.getNotebooksById(userOptional.get().getId(), start, null);
        User user = userOptional.get();
        List<Notebook> notebooks = userRepository.getNotebooksById(user.getId());

        req.setAttribute("start", start);
        req.setAttribute("step", step);
        req.setAttribute("user", user);
        req.setAttribute("notebooks", notebooks);
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}
