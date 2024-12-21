package ru.itis.mydiary.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

@WebFilter("/*")
public class CsrfFilter extends HttpFilter {
    private static final SecureRandom random = new SecureRandom();
    private static final Encoder base64Encoder = Base64.getUrlEncoder();
    private static final List<String> exceptions = List.of("/css", "/jsp", "/icons", "/WEB-INF", "/img", "/images", "/downloadImage");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = req.getSession();
        if (inException(req.getRequestURI())) {
            chain.doFilter(req, res);
            return;
        }


        if (req.getMethod().equals("GET")) {
            String csrfToken = generateCsrfToken();

            session.setAttribute("csrf_token", csrfToken);
            req.setAttribute("csrf_token", csrfToken);

            chain.doFilter(req, res);

        } else if (req.getMethod().equals("POST")) {
            String expectedCsrfToken = (String) session.getAttribute("csrf_token");
            String actualCsrfToken = req.getParameter("csrf_token");

            if (expectedCsrfToken != null && expectedCsrfToken.equals(actualCsrfToken)) {
                chain.doFilter(req, res);
            } else {
                res.setStatus(403);
                res.getWriter().println("Csrf not match");
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean inException(String uri) {
        return exceptions.stream().anyMatch(uri::contains);
    }

    private String generateCsrfToken() {
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
