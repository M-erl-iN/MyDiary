package ru.itis.mydiary.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ru.itis.mydiary.filters.AuthFilter;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

public class AuthUtils {
    public static boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean checkPassword(String password) {
        return password.matches("^(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[0-9]).{5,}$");
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static void clearSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.setAttribute(AuthFilter.AUTHORIZATION, false);
            session.setAttribute("cookie_token", null);
            session.invalidate();
        }
    }

    public static void clearCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookie = req.getCookies();
        for (Cookie c : cookie) {
            c.setValue(null);
            resp.addCookie(c);
        }
    }

    public static void clearCookie(HttpServletRequest req, HttpServletResponse resp, String cookieName) {
        Cookie[] cookie = req.getCookies();
        for (Cookie c : cookie) {
            if (c.getName().equals(cookieName)) {
                c.setValue(null);
                resp.addCookie(c);
            }
        }
    }

    public static boolean checkEntity(Optional<?> opt, HttpServletRequest req, HttpServletResponse resp, String errorMessage) throws IOException {
        if (opt.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error&err=" + errorMessage);
            return false;
        } return true;
    }

    public static boolean checkPhone(Long phone) {
        return true;
    }

    public static boolean checkBirthdate(Date birthdate) {
        return true;
    }
}
