package ru.itis.mydiary.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.mydiary.dto.InvitePost;
import ru.itis.mydiary.entity.Invite;
import ru.itis.mydiary.mapper.InviteRowMapper;
import ru.itis.mydiary.repositories.InviteRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InviteRepositoryImpl implements InviteRepository {
    private final InviteRowMapper inviteRowMapper;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_ALL = "SELECT * FROM invites";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM invites where id = ?";
    private static final String SQL_FIND_BY_USER_SEND_ID = "SELECT * FROM invites where user_send_id = ?";
    private static final String SQL_FIND_BY_USER_INVITE_ID = "SELECT * FROM invites where user_invite_id = ?";
    private static final String SQL_SAVE =
            "INSERT INTO invites (user_send_id, user_invite_id, notebook_id, role_id) VALUES (?, ?, ?, ?) returning *";
    private static final String SQL_SAVE_ = "insert into users_notebooks_roles (user_id, notebook_id, role_id) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM invites WHERE id = ?";


    public InviteRepositoryImpl(DataSource dataSource, InviteRowMapper inviteRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.inviteRowMapper = inviteRowMapper;
    }


    @Override
    public List<Invite> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, inviteRowMapper);
    }

    @Override
    public Optional<Invite> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, inviteRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Invite> findByUserSendId(Long userSendId) {
        return jdbcTemplate.query(SQL_FIND_BY_USER_SEND_ID, inviteRowMapper, userSendId);
    }

    @Override
    public List<Invite> findByUserInviteId(Long userInviteId) {
        return jdbcTemplate.query(SQL_FIND_BY_USER_INVITE_ID, inviteRowMapper, userInviteId);
    }

    @Override
    public Optional<Invite> save(Invite invite) {
        try {
            Invite savedInvite = jdbcTemplate.queryForObject(
                    SQL_SAVE, inviteRowMapper,
                    invite.getUserSendId(),
                    invite.getUserInviteId(),
                    invite.getNotebookId(),
                    invite.getRoleId());
            return Optional.of(savedInvite);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Invite invite) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) > 0;
    }

    @Override
    public boolean accept(Invite invite) {
        return jdbcTemplate.update(SQL_SAVE_, invite.getUserInviteId(), invite.getNotebookId(), invite.getRoleId()) > 0;
    }
}
