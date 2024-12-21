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
import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.NoteRowMapper;
import ru.itis.mydiary.repositories.NoteRepository;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.RoleInteractionRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.NoteService;
import ru.itis.mydiary.service.impl.NotebookServiceImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name="note", value="/note")
public class NoteServlet extends HttpServlet {
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private NotebookServiceImpl notebookService;
    private NotebookRepository notebookRepository;
    private NoteRowMapper noteRowMapper;
    private NoteRepository noteRepository;
    private RoleInteractionRepository roleInteractionRepository;
    private NoteService noteService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserServiceImpl) context.getAttribute("userService");
        userRepository = (UserRepository) context.getAttribute("userRepository");
        notebookService = (NotebookServiceImpl) context.getAttribute("notebookService");
        notebookRepository = (NotebookRepository) context.getAttribute("notebookRepository");
        roleInteractionRepository = (RoleInteractionRepository) context.getAttribute("roleInteractionRepository");
        noteRowMapper = (NoteRowMapper) context.getAttribute("noteRowMapper");
        noteRepository = (NoteRepository) context.getAttribute("noteRepository");
        noteService = (NoteService) context.getAttribute("noteService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        User userFromCookie = userRepository.findByCookieToken(cookieToken).get();

        Long noteId = Long.parseLong(req.getParameter("nId"));
        String success = req.getParameter("success");
        if (success == null) success = "dontUse";

        Optional<Note> noteOptional = noteRepository.findById(noteId);

        if (noteOptional.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?err=Incorrect note identification");
            return;
        }

        Notebook notebook = notebookRepository.findById(noteOptional.get().getNotebookId()).get();
        List<Long> usersId = roleInteractionRepository.getUsersIdByNotebookId(notebook.getId());

//        if (notebookOptional.isEmpty()) {
//            resp.sendRedirect(req.getContextPath() + "/error?err=Incorrect note notebook identification");
//            return;
//        }

        if (!usersId.contains(userFromCookie.getId())) {
            resp.sendRedirect(req.getContextPath() + "/error?err=Incorrect user identification");
            return;
        }

        if (noteOptional.get().getCreatorId() == userFromCookie.getId()) {
            req.setAttribute("role", "creator");
        } else {
            req.setAttribute("role", "spectator");
        }

        Note note = noteOptional.get();

        req.setAttribute("success", success);
        req.setAttribute("user", userFromCookie);
        req.setAttribute("note", note);
        req.getRequestDispatcher("/jsp/note.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");
        User userFromCookie = userRepository.findByCookieToken(cookieToken).get();
        Note note = noteRepository.findById(Long.parseLong(req.getParameter("nId"))).get();

        if (userFromCookie.getId() != note.getCreatorId()) {
            resp.sendRedirect(req.getContextPath() + "/error?err=Incorrect user for this note");
            return;
        }

        note.setTitle(req.getParameter("title"));
        String message = req.getParameter("message");
        note.setText(message);

        System.out.println("NOTE: " + note);
        boolean updated = noteRepository.update(note);
        if (!updated) {
            resp.sendRedirect(req.getContextPath() + "/note?nId=" + note.getId() + "&success=false");
        } resp.sendRedirect(req.getContextPath() + "/note?nId=" + note.getId() + "&success=true");
    }
}
