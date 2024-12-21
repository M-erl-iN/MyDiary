package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.dto.InvitePost;
import ru.itis.mydiary.entity.Invite;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.InviteRepository;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.repositories.impl.RoleInteractionRepositoryImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "invites", value = "/invites")
public class InvitesServlet extends HttpServlet {
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private RoleInteractionRepositoryImpl roleRepository;
    private InviteRepository inviteRepository;
    private NotebookRepository notebookRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepository) config.getServletContext().getAttribute("userRepository");
        inviteRepository = (InviteRepository) config.getServletContext().getAttribute("inviteRepository");
        roleRepository = (RoleInteractionRepositoryImpl) config.getServletContext().getAttribute("roleInteractionRepository");
        notebookRepository = (NotebookRepository) config.getServletContext().getAttribute("notebookRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("dId") != null) {
            Long deleteId = Long.parseLong(req.getParameter("dId"));
            inviteRepository.deleteById(deleteId);
            resp.sendRedirect(req.getContextPath() + "/invites");
            return;
        } else if (req.getParameter("cId") != null) {
            Long accessId = Long.parseLong(req.getParameter("cId"));
            inviteRepository.accept(inviteRepository.findById(accessId).get());
            inviteRepository.deleteById(accessId);
            resp.sendRedirect(req.getContextPath() + "/invites");
            return;
        }
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");
        Optional<User> user = userRepository.findByCookieToken(cookieToken);
        long start = 0l;
        int step = 6;
        if (req.getParameter("start") != null) {
            start = Long.parseLong(req.getParameter("start"));
            if (start < 0) {
                start = 0;
            }
        }

        List<Invite> invites = inviteRepository.findByUserInviteId(user.get().getId());
        List<InvitePost> myInvites = toInvitePost(invites);

        List<Invite> invitesSends = inviteRepository.findByUserSendId(user.get().getId());
        List<InvitePost> mySends = toInvitePost(invitesSends);

        req.setAttribute("start", start);
        req.setAttribute("step", step);
        req.setAttribute("nickname", user.get().getNickname());
        req.setAttribute("myInvites", myInvites);
        req.setAttribute("mySends", mySends); //кто кому в какой дневник и роль
        req.getRequestDispatcher("/jsp/invites.jsp").forward(req, resp);
    }

    public List<InvitePost> toInvitePost(List<Invite> invites) {
        List<InvitePost> invitePosts = new ArrayList<>();
        InvitePost tempInvitePost;
        for (Invite invite: invites) {
            try {
                tempInvitePost = InvitePost.builder()
                        .inviteId(invite.getId())
                        .userSendName(userRepository.findById(invite.getUserSendId()).get().getNickname())
                        .userInviteName(userRepository.findById(invite.getUserInviteId()).get().getNickname())
                        .notebookTitle(notebookRepository.findById(invite.getNotebookId()).get().getTitle())
                        .build();
                invitePosts.add(tempInvitePost);
            } catch (Exception e) {
                System.out.println("Invite Servlet error: " + e.getMessage());
            }
        } return invitePosts;
    }
}