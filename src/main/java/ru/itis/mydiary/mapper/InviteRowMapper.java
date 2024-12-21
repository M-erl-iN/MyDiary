package ru.itis.mydiary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.mydiary.entity.Invite;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteRowMapper implements RowMapper<Invite> {
    public Invite mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Invite.builder()
                .id((Long) rs.getObject("id"))
                .userSendId(rs.getLong("user_send_id"))
                .userInviteId(rs.getLong("user_invite_id"))
                .notebookId(rs.getLong("notebook_id"))
                .roleId(rs.getLong("role_id"))
                .build();
    }
}
