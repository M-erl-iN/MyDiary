package ru.itis.mydiary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.mydiary.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Role.builder()
                .id(rs.getLong("id"))
                .name((String) rs.getObject("name"))
                .build();
    }
}
