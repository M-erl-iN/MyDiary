package ru.itis.mydiary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.mydiary.entity.RoleInteraction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleInteractionRowMapper implements RowMapper<RoleInteraction> {
    public RoleInteraction mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RoleInteraction.builder()
                .id((Long) rs.getObject("id"))
                .userId((Long) rs.getObject("user_id"))
                .notebookId((Long) rs.getObject("notebook_id"))
                .roleId((Long) rs.getObject("role_id"))
                .build();
    }
}
