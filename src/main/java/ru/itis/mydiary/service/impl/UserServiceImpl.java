package ru.itis.mydiary.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.itis.mydiary.dto.AuthResponse;
import ru.itis.mydiary.dto.UserAuthEmailDto;
import ru.itis.mydiary.dto.UserRegistrationDto;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.UserRowMapper;
import ru.itis.mydiary.repositories.UserRepository;
import ru.itis.mydiary.service.UserService;
import ru.itis.mydiary.util.AuthUtils;

import java.util.Optional;


@Getter
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRowMapper userRowMapper;

    @Override
    public AuthResponse signIn(UserAuthEmailDto user) {
        if (user.getPassword() == null || user.getEmail() == null) {
            return response(1, "Empty Data", null);}

        if (!AuthUtils.checkPassword(user.getPassword())) {
            return response(2, "Weak Password", null);}

        if (!AuthUtils.checkEmail(user.getEmail())) {
            return response(3, "Invalid Email", null);}

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isEmpty()) {
            return response(5, "Email not found", null);}

        if (!AuthUtils.verifyPassword(user.getPassword(), userOptional.get().getHashPassword())) {
            return response(6, "Password mismatch", null);
        }

        return response(0, "OK", userOptional.get());
    }

    @Override
    public AuthResponse signUp(UserRegistrationDto user) {
        if (user.getNickname() == null || user.getPassword() == null || user.getEmail() == null) {
            return response(1, "Empty Data", null);}

        if (!AuthUtils.checkPassword(user.getPassword())) {
            return response(2, "Weak Password", null);}

        if (!AuthUtils.checkEmail(user.getEmail())) {
            return response(3, "Invalid Email", null);}

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return response(4, "Email Already Exists", null);}

        if (user.getPhone() != null && !AuthUtils.checkPhone(user.getPhone())) {
            return response(7, "Invalid Phone Number", null);}

        if (user.getBirthdate() != null && !AuthUtils.checkBirthdate(user.getBirthdate())) {
            return response(8, "Invalid Date", null);}

//        System.out.println(UserRowMapper.toEntity(user));
        Optional<User> userOptional = userRepository.save(UserRowMapper.toEntity(user));
//        System.out.println(userOptional.isPresent());

        if (userOptional.isEmpty()) {
            return response(99, "Database process error", null);}

        return response(0, "OK", userOptional.get());
    }

    @Override
    public AuthResponse update(User user, HttpServletRequest req) {
        try {
            String nickname = req.getParameter("nickname");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            if (nickname != null) {user.setNickname(nickname);}
            if (email != null) {user.setEmail(email);}
            if (phone != null) {user.setPhone(Long.valueOf(phone));}
            userRepository.update(user);
            return new AuthResponse(0, "Update user data success", user);
        } catch (Exception e) {
            return new AuthResponse(99, "Update user data error", user);
        }
    }

    private AuthResponse response(int status, String statusDesc, User user) {
        return AuthResponse.builder()
                .status(status)
                .statusDesc(statusDesc)
                .user(user)
                .build();
    }
}
