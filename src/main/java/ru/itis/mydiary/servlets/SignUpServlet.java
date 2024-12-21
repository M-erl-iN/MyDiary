package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.mydiary.dto.AuthResponse;
import ru.itis.mydiary.dto.UserRegistrationDto;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.filters.AuthFilter;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;
import ru.itis.mydiary.util.AuthUtils;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;


@WebServlet(name="signUp", value="/signUp")
public class SignUpServlet extends HttpServlet {
    private UserServiceImpl userService;
    private UserRepositoryImpl userRepository;


    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepositoryImpl) config.getServletContext().getAttribute("userRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Cookie> cookieTokenOptional = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("cookie_token"))
                .findFirst();

        String message = null;
        if (req.getParameter("message") != null) {message = req.getParameter("message");}

        if (cookieTokenOptional.isPresent()) {
            String cookieToken = cookieTokenOptional.get().getValue();
            Optional<User> userOptional = userService.getUserRepository().findByCookieToken(cookieToken);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                HttpSession session = req.getSession(true);
                session.setAttribute(AuthFilter.AUTHORIZATION, true);
                session.setAttribute("userName", user.getNickname());
                resp.sendRedirect(req.getContextPath() + "/profile");
                return;
            }
            AuthUtils.clearCookie(req, resp, "cookie_token");
        }
        if (message != null) { req.setAttribute("message", message);}
        req.getRequestDispatcher("/jsp/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegistrationDto userDto = UserRegistrationDto.builder()
                .nickname(req.getParameter("nickname"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .phone(Long.valueOf(req.getParameter("phone")))
                .birthdate(Date.valueOf(req.getParameter("birthdate")))
                .build();

        AuthResponse authResponse = userService.signUp(userDto);

        if (authResponse.getStatus() == 0) {
            HttpSession session = req.getSession(true);
            session.setAttribute(AuthFilter.AUTHORIZATION, true);

            String cookieToken = UUID.randomUUID().toString();
            userRepository.setCookieToken(userDto.getEmail(), cookieToken);
            session.setAttribute("cookie_token", cookieToken);

            String remember = req.getParameter("remember");
            if (remember != null && remember.equals("on")) {
                Cookie cookieCookie = new Cookie("cookie_token", cookieToken);
                cookieCookie.setMaxAge(60 * 60);
                resp.addCookie(cookieCookie);
            }

            resp.sendRedirect(req.getContextPath() + "/profile");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/error?err=" + authResponse.getStatusDesc());
        }
    }
}
