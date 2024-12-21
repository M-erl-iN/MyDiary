package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.entity.Note;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.NoteRepository;
import ru.itis.mydiary.repositories.RoleInteractionRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.NoteService;
import ru.itis.mydiary.service.UserService;
import ru.itis.mydiary.util.AuthUtils;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name="deleteNote", value="/deleteNote")
public class DeleteNoteServlet extends HttpServlet {
    private UserRepository userRepository;
    private NoteRepository noteRepository;
    private UserService userService;
    private NoteService noteService;
    private RoleInteractionRepository roleInteractionRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userRepository = (UserRepositoryImpl) context.getAttribute("userRepository");
        noteRepository = (NoteRepository) context.getAttribute("noteRepository");
        userService = (UserService) context.getAttribute("userService");
        noteService = (NoteService) context.getAttribute("noteService");
        roleInteractionRepository = (RoleInteractionRepository) context.getAttribute("roleInteractionRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);
        if (AuthUtils.checkEntity(userOptional, req, resp, "bad data")) {return;}

        Long noteId = Long.parseLong(req.getParameter("nId"));
        Long notebookId = Long.parseLong(req.getParameter("bId"));

        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (AuthUtils.checkEntity(noteOptional, req, resp, "bad data")) {return;}

        if (noteOptional.get().getCreatorId() != userOptional.get().getId()) {
            resp.sendRedirect(req.getContextPath() + "/error?err=bad data"); return;
        }

        User user = userOptional.get();
        Note note = noteOptional.get();

        noteRepository.deleteById(noteId);
        resp.sendRedirect(req.getContextPath() + "/diary?nId=" + notebookId);
    }
}
