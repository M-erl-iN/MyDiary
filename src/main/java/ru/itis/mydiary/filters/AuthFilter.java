package ru.itis.mydiary.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.repositories.impl.UserRepositoryImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;
import ru.itis.mydiary.util.AuthUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@WebFilter("/*")
public class AuthFilter implements Filter {
    private UserServiceImpl userService;
    private UserRepositoryImpl userRepository;


    private static final String DOMAIN = "/MyDiary_war_exploded";

    private static List<String> PROTECTED_URIS = List.of(
            "/logout", "/profile", "/createDiary", "/createNote",
            "/deleteDiary", "/deleteNote", "/diary", "/note", "/invites"
    );
    private static List<String> NOTAUTH_URIS = List.of("/signIn", "/signUp");
    private static final List<String> NOT_CONTROLLED_URIS = List.of("/css", "/jsp", "/icons", "/WEB-INF", "/img", "/images", "/downloadImage");
    private static final List<String> PUBLIC_URIS = List.of("/error");

    private static final String PROTECTED_REDIRECT = DOMAIN + "/signIn";
    private static final String NOTAUTH_REDIRECT = DOMAIN + "/profile";
    private static final String INCORRECT_REDIRECT = DOMAIN + "/profile";

    public static final String AUTHORIZATION = "authorization";

    @Override
    public void init(FilterConfig filterConfig) {
        userService = (UserServiceImpl) filterConfig.getServletContext().getAttribute("userService");
        userRepository = (UserRepositoryImpl) filterConfig.getServletContext().getAttribute("userRepository");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        String correctUri = getCorrectUri(uri);

        if (isNotControlledUri(correctUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean notAuth = isNotAuthUri(correctUri);
        boolean protect = isProtectedUri(correctUri);
        boolean isAuth = isUserAuth(request);


        if (isAuth && notAuth) response.sendRedirect(NOTAUTH_REDIRECT);
        else if (!isAuth && protect) response.sendRedirect(PROTECTED_REDIRECT);
        else filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isProtectedUri(String uri) {
        return PROTECTED_URIS.stream().anyMatch(uri::contains);
    }

    private boolean isNotAuthUri(String uri) {
        return NOTAUTH_URIS.stream().anyMatch(uri::contains);
    }

    private boolean isPublicUri(String uri) {
        return PUBLIC_URIS.stream().anyMatch(uri::contains);
    }

    private static String getCorrectUri(String uri) {
        return uri.substring(DOMAIN.length());
    }

    private boolean isNotControlledUri(String uri) {
        return NOT_CONTROLLED_URIS.stream().anyMatch(uri::contains);
    }

    private boolean isUserAuthAnalog(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String cookieTokenFromSession = (String) session.getAttribute("cookie_token");
        boolean cookieFlag = false;
        boolean sessionFlag = false;

        Optional<Cookie> cookieTokenFromCookieOptional = null;

        if (request.getCookies() != null) {
            cookieTokenFromCookieOptional = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("cookie_token"))
                    .findFirst();
            if (cookieTokenFromCookieOptional.isPresent()) {
                Optional<User> userOptionalFromSession = userRepository.findByCookieToken(cookieTokenFromCookieOptional.get().getValue());
                if (userOptionalFromSession.isPresent()) {
                    cookieFlag = true;
                } else {
                    AuthUtils.clearCookie(request, response, "cookie_token");
                }
            }
        }

        if (cookieTokenFromSession != null) {
            Optional<User> userOptionalFromSession = userRepository.findByCookieToken(cookieTokenFromSession);
            if (userOptionalFromSession.isPresent()) {
                sessionFlag = true;
            } else {
                AuthUtils.clearSession(request);
            }
        }

        if (!sessionFlag && !cookieFlag) {
            return false;
        } else if (!sessionFlag && cookieFlag) {
            session.setAttribute("cookie_token", cookieTokenFromCookieOptional.get().getValue());
        }
        return true;
    }

    private boolean isUserAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) return false;

        Boolean flag = (Boolean) session.getAttribute(AUTHORIZATION);
        return flag != null && flag;
    }
}
