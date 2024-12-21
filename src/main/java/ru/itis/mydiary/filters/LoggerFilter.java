package ru.itis.mydiary.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@WebFilter("/*")
public class LoggerFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
//        System.out.println("[my ----- REQUEST]: \n\tURI: " + req.getRequestURI() + "\n\tMETHOD: " + req.getMethod() + "\n");
        logger.info("LOGGERFILTER - [REQUEST]: URI: {} / METHOD: {}", req.getRequestURI(), req.getMethod());
        super.doFilter(req, res, chain);
//        System.out.println("[my ----- RESPONSE]: \n\tSTATUS: " + res.getStatus() + "\n");
        logger.info("[LOGGERFILTER - [REQUEST]: STATUS: {}", res.getStatus());
    }
}
