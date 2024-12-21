package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.entity.Invite;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.InviteRowMapper;
import ru.itis.mydiary.repositories.InviteRepository;
import ru.itis.mydiary.repositories.NoteRepository;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.NoteService;
import ru.itis.mydiary.service.NotebookService;
import ru.itis.mydiary.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name="inviteUserToNotebook", value="/inviteUserToNotebook")
public class AddUserToNotebookServlet extends HttpServlet {
    private UserService userService;
    private UserRepository userRepository;
    private NotebookRepository notebookRepository;
    private NotebookService notebookService;
    private NoteRepository noteRepository;
    private NoteService noteService;
    private InviteRowMapper inviteRowMapper;
    private InviteRepository inviteRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        userRepository = (UserRepository) context.getAttribute("userRepository");
        notebookRepository = (NotebookRepository) context.getAttribute("notebookRepositoryImpl");
        notebookService = (NotebookService) context.getAttribute("notebookService");
        noteService = (NoteService) context.getAttribute("noteService");
        inviteRowMapper = (InviteRowMapper) context.getAttribute("inviteRowMapper");
        inviteRepository = (InviteRepository) context.getAttribute("inviteRepository");
    super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String cookieToken = (String) session.getAttribute("cookie_token");

        Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);

        Long userSendId = userOptional.get().getId();
        Long role = Long.valueOf(req.getParameter("role"));
        String userInviteEmail = req.getParameter("userInvite");
        Long userInviteId = userRepository.findByEmail(userInviteEmail).get().getId();
        Long notebookId = Long.parseLong(req.getParameter("nId"));

        Invite invite = Invite.builder()
                .userSendId(userSendId)
                .userInviteId(userInviteId)
                .notebookId(notebookId)
                .roleId(role)
                .build();
        Optional<Invite> inviteOptional = inviteRepository.save(invite);
        if (inviteOptional.isPresent()) {
            resp.sendRedirect(req.getContextPath() + "/diary?nId=" + notebookId);
        }
    }
}
