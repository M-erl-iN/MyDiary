package ru.itis.mydiary.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.itis.mydiary.dto.AuthResponse;
import ru.itis.mydiary.dto.UserAuthEmailDto;
import ru.itis.mydiary.dto.UserRegistrationDto;
import ru.itis.mydiary.entity.User;

public interface UserService {
    AuthResponse signIn(UserAuthEmailDto user);
    AuthResponse signUp(UserRegistrationDto user);
    AuthResponse update(User user, HttpServletRequest req);
}