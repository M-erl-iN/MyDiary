package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.entity.Note;
import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.NoteRowMapper;
import ru.itis.mydiary.repositories.NoteRepository;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.RoleInteractionRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.impl.NotebookServiceImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name="diary", value="/diary")
public class DiaryServlet extends HttpServlet {
    UserServiceImpl userService;
    UserRepository userRepository;
    NotebookServiceImpl notebookService;
    NotebookRepository notebookRepository;
    NoteRowMapper noteRowMapper;
    NoteRepository noteRepository;
    RoleInteractionRepository roleInteractionRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepository) config.getServletContext().getAttribute("userRepository");
        notebookService = (NotebookServiceImpl) config.getServletContext().getAttribute("notebookService");
        notebookRepository = (NotebookRepository) config.getServletContext().getAttribute("notebookRepository");
        roleInteractionRepository = (RoleInteractionRepository) config.getServletContext().getAttribute("roleInteractionRepository");
        noteRowMapper = (NoteRowMapper) config.getServletContext().getAttribute("noteRowMapper");
        noteRepository = (NoteRepository) config.getServletContext().getAttribute("noteRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        Long notebookId = Long.parseLong(req.getParameter("nId"));
        Optional<Notebook> notebookOptional = notebookRepository.findById(notebookId);

        if (notebookOptional.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?err=Incorrect notebook identification");
            return;
        }

        User user = userRepository.findByCookieToken(cookieToken).get();
        List<Long> usersId = roleInteractionRepository.getUsersIdByNotebookId(notebookId);

        if (!usersId.contains(user.getId())) {
            resp.sendRedirect(req.getContextPath() + "/error?err=Incorrect notebook identification (incorrect user)");
            return;
        }

        long start = 0l;
        int step = 6;
        if (req.getParameter("start") != null) {
            start = Long.parseLong(req.getParameter("start"));
            if (start < 0) {
                start = 0;
            }
        }

        List<Note> notes = noteRepository.getNotesByNotebookId(notebookId);

        req.setAttribute("start", start);
        req.setAttribute("step", step);
        req.setAttribute("nickname", user.getNickname());
        req.setAttribute("title", notebookOptional.get().getTitle());
        req.setAttribute("user", user);
        req.setAttribute("notes", notes);
        req.setAttribute("flag", roleInteractionRepository.getRoleByUserIdAndNoteBookId(user.getId(), notebookId) != 3);
        req.getRequestDispatcher("/jsp/diary.jsp").forward(req, resp);
    }
}
