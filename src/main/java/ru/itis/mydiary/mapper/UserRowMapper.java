package ru.itis.mydiary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.mydiary.dto.UserRegistrationDto;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.util.AuthUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id((Long) rs.getObject("id"))
                .nickname((String) rs.getObject("nickname"))
                .email((String) rs.getObject("email"))
                .phone(rs.getLong("phone"))
                .hashPassword((String) rs.getObject("hash_password"))
                .birthdate((Date) rs.getObject("birthdate"))
                .cookieToken((String) rs.getObject("cookie_token"))
                .imageId((String) rs.getObject("image_id"))
                .build();
    }

    public static User toEntity(UserRegistrationDto userRegistrationDto) {
        return User.builder()
                .nickname(userRegistrationDto.getNickname())
                .email(userRegistrationDto.getEmail())
                .phone(userRegistrationDto.getPhone())
                .hashPassword(AuthUtils.hashPassword(userRegistrationDto.getPassword()))
                .birthdate(userRegistrationDto.getBirthdate())
                .build();
    }
}
