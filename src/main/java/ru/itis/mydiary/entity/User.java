package ru.itis.mydiary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String nickname;
    private String email;
    private Long phone;
    private String hashPassword;
    private Date birthdate;

    private String cookieToken;
    private String imageId;
}