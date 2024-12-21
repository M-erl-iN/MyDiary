package ru.itis.mydiary.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.mydiary.entity.Role;
import ru.itis.mydiary.entity.RoleInteraction;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.RoleInteractionRowMapper;
import ru.itis.mydiary.mapper.RoleRowMapper;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.repositories.RoleInteractionRepository;
import ru.itis.mydiary.repositories.UserRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RoleInteractionRepositoryImpl implements RoleInteractionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper;
    private final RoleInteractionRowMapper roleInteractionRowMapper;
    private final UserRepository userRepository;

    private static final String SQL_FIND_ALL = "select * from roles";
    private static final String SQL_FIND_BY_ID = "select * from roles where id = ?";
    private static final String SQL_SAVE = "insert into roles (name) values (?)";
    private static final String SQL_DELETE_BY_ID = "delete from roles where id = ?";
    private static final String SQL_UPDATE = "update roles set name = ? where id = ?";

    private static final String SQL_GET_USERS_ID_AND_ROLES_ID_BY_NOTEBOOK_ID =
            "select user_id, role_id from users_notebooks_roles where notebook_id = ?";
    private static final String SQL_GET_USERS_ID_BY_NOTEBOOK_ID =
            "select user_id from users_notebooks_roles where notebook_id = ?";
    private static final String SQL_GET_ROLE_BY_USER_ID_AND_NOTEBOOK_ID =
            "select role_id from users_notebooks_roles where notebook_id = ? and user_id = ?";
    private static final String SQL_DELETE_BY_USER_ID_AND_NOTEBOOK_ID =
            "delete from users_notebooks_roles where user_id = ? and notebook_id = ?";

//    getUsersAndRolesByNotebookId;
//    getUsersByNotebookId;
//    getRoleByUserIdAndNoteBookId;

    public RoleInteractionRepositoryImpl(DataSource dataSource,
                                         RoleRowMapper roleRowMapper,
                                         RoleInteractionRowMapper roleInteractionRowMapper,
                                         UserRepository userRepository,
                                         NotebookRepository notebookRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.roleRowMapper = roleRowMapper;
        this.roleInteractionRowMapper = roleInteractionRowMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<Role> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, roleRowMapper);
    }

    @Override
    public Optional<Role> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, roleRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> save(Role role) {
        try {
            int affectedRows = jdbcTemplate.update(
                    SQL_SAVE,
                    role.getName()
            );

            if (affectedRows > 0) {
                return Optional.of(role);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Role role) {
        return jdbcTemplate.update(SQL_UPDATE, roleRowMapper, role.getName()) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) > 0;
    }

    @Override
    public HashMap<User, Role> getUsersIdAndRolesIdByNotebookId(Long id) {
        List<RoleInteraction> roleInteractionList = jdbcTemplate.query(SQL_GET_USERS_ID_AND_ROLES_ID_BY_NOTEBOOK_ID, roleInteractionRowMapper, id);
        HashMap<User, Role> result = new HashMap<>();
        roleInteractionList.forEach(roleInteraction -> result.put(
                userRepository.findById(roleInteraction.getUserId()).get(),
                findById(roleInteraction.getRoleId()).get()));
        return result;
    }

    @Override
    public Long getRoleByUserIdAndNoteBookId(Long userId, Long notebookId) {
        try {
            return jdbcTemplate.queryForObject(
                            SQL_GET_ROLE_BY_USER_ID_AND_NOTEBOOK_ID,
                            Long.class,
                            userId,
                            notebookId);
        } catch (EmptyResultDataAccessException e) {
            return 4l;
        }
    }

    @Override
    public List<Long> getUsersIdByNotebookId(Long notebookId) {
        return jdbcTemplate.queryForList(SQL_GET_USERS_ID_BY_NOTEBOOK_ID, Long.class, notebookId);
    }

    @Override
    public boolean deleteByUserIdAndNotebookId(Long userId, Long notebookId) {
        return jdbcTemplate.update(SQL_DELETE_BY_USER_ID_AND_NOTEBOOK_ID, userId, notebookId) > 0;
    }
}
