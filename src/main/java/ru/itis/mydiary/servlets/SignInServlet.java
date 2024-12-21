package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.mydiary.dto.AuthResponse;
import ru.itis.mydiary.dto.UserAuthEmailDto;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.filters.AuthFilter;
import ru.itis.mydiary.repositories.MongoFileRepository;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;
import ru.itis.mydiary.util.AuthUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.io.FileInputStream;


@WebServlet(name="signIn", value="/signIn")
public class SignInServlet extends HttpServlet {
    private UserServiceImpl userService;
    private UserRepositoryImpl userRepository;
    private MongoFileRepository mongoFileRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepositoryImpl) config.getServletContext().getAttribute("userRepository");
        mongoFileRepository = (MongoFileRepository) config.getServletContext().getAttribute("mongoFileRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<Cookie> cookieTokenOptional = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("cookie_token"))
                    .findFirst();
            if (cookieTokenOptional.isPresent()) {
                String cookieToken = cookieTokenOptional.get().getValue();
                Optional<User> userOptional = userRepository.findByCookieToken(cookieToken);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    HttpSession session = req.getSession(true);

                    session.setAttribute(AuthFilter.AUTHORIZATION, true);
                    session.setAttribute("cookie_token", user.getCookieToken());
                    resp.sendRedirect( req.getContextPath() + "/profile");
                    return;
                }
                AuthUtils.clearCookie(req, resp, "cookie_token");
            }
        } catch (NullPointerException e) {
            resp.sendRedirect(req.getContextPath() + "/error?err=" + e.getMessage());
            return;
        }

        String message = null;
        if (req.getParameter("message") != null) {message = req.getParameter("message");}

        if (message != null) { req.setAttribute("message", message);}
        req.getRequestDispatcher("/jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserAuthEmailDto userDto = UserAuthEmailDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        AuthResponse authResponse = userService.signIn(userDto);

        if (authResponse.getStatus() == 0) {
            HttpSession session = req.getSession(true);
            session.setAttribute(AuthFilter.AUTHORIZATION, true);

            String cookieToken = UUID.randomUUID().toString();
            userRepository.setCookieToken(userDto.getEmail(), cookieToken);
            session.setAttribute("cookie_token", cookieToken);

            String remember = req.getParameter("remember");
            if (remember != null && remember.equals("on")) {
                Cookie uuidCookie = new Cookie("cookie_token", cookieToken);
                uuidCookie.setMaxAge(60 * 60);
                resp.addCookie(uuidCookie);
            }
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            resp.sendRedirect(req.getContextPath() + "/error?err=" + authResponse.getStatusDesc());
        }
    }
}
