package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.dto.CreateNoteDto;
import ru.itis.mydiary.dto.CreateNoteResponse;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.NoteRepository;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.NoteService;
import ru.itis.mydiary.service.NotebookService;
import ru.itis.mydiary.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name="createNote", value="/createNote")
public class CreateNoteServlet extends HttpServlet {
    private UserService userService;
    private UserRepository userRepository;
    private NotebookRepository notebookRepository;
    private NotebookService notebookService;
    private NoteRepository noteRepository;
    private NoteService noteService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        userRepository = (UserRepository) context.getAttribute("userRepository");
        notebookRepository = (NotebookRepository) context.getAttribute("notebookRepositoryImpl");
        notebookService = (NotebookService) context.getAttribute("notebookService");
        noteService = (NoteService) context.getAttribute("noteService");
    super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateNoteDto noteDto = CreateNoteDto.builder()
                .title(req.getParameter("title"))
                .build();


        Long notebookId = Long.parseLong(req.getParameter("nId"));

        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");

        Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);
        CreateNoteResponse createResponse = noteService.createNote(noteDto, userOptional.get().getId(), notebookId);

        if (createResponse.getStatus() == 0) {
            resp.sendRedirect(req.getContextPath() + "/diary?nId=" + notebookId);
        }
    }
}
