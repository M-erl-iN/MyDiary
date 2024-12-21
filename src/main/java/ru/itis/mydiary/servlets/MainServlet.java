package ru.itis.mydiary.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@WebServlet(name="main", value="/main")
public class MainServlet extends HttpServlet {
    UserServiceImpl userService;
    UserRepository userRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
        userRepository = (UserRepository) config.getServletContext().getAttribute("userRepository");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String cookieToken = (String) session.getAttribute("cookie_token");
        if (cookieToken == null) {
            Optional<Cookie> cookieTokenOptional = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("cookie_token"))
                    .findFirst();
            if (cookieTokenOptional.isPresent()) {
                cookieToken = cookieTokenOptional.get().getValue();
                session.setAttribute("cookieToken", cookieToken);
                System.out.println("Attribute cookieToken has been set");
            }
        }

        List<User> users = userRepository.findAll()
                .stream()
                .map(user -> User.builder()
                        .nickname(user.getNickname())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
        req.setAttribute("users", users);

        req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
    }
}
