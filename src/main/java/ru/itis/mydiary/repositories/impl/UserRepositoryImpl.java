package ru.itis.mydiary.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.NotebookRowMapper;
import ru.itis.mydiary.mapper.UserRowMapper;
import ru.itis.mydiary.repositories.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final UserRowMapper userRowMapper;
    private final NotebookRowMapper notebookRowMapper;
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ALL = "SELECT * FROM users";
    private final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private final String SQL_FIND_BY_PHONE = "SELECT * FROM users WHERE phone = ?";
    private final String SQL_FIND_BY_COOKIE_TOKEN = "SELECT * FROM users WHERE cookie_token = ?";

    private final String SQL_SET_COOKIE_TOKEN = "UPDATE users SET cookie_token = ? WHERE id = ?";
    private final String SQL_SET_COOKIE_TOKEN_BY_EMAIL = "UPDATE users SET cookie_token = ? WHERE email = ?";
    private final String SQL_SAVE =
            "INSERT INTO users (nickname, email, phone, hash_password, birthdate) VALUES (?, ?, ?, ?, ?) returning *";
    private static final String SQL_TEST =
            "insert into users (nickname, email, phone, hash_password, birthdate) values (?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE users SET nickname = ?, email = ?, phone = ?, hash_password = ?, birthdate = ? WHERE id = ?";
    private final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String SQL_GET_NOTEBOOKS_BY_USER_ID =
            "select * from notebooks where id in (    select notebook_id from users_notebooks_roles where user_id = ?    )";
    private static final String SQL_GET_NOTEBOOKS_BY_USER_ID_WITH_LIMIT =
            "select * from notebooks where id in (    select notebook_id from users_notebooks_roles where user_id = ?    ) offset ? limit ?";
    private static final String SQL_SET_IMAGE_ID_BY_ID = "update users set image_id = ? where id = ?";


    public UserRepositoryImpl(DataSource dataSource, UserRowMapper userRowMapper, NotebookRowMapper notebookRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userRowMapper = userRowMapper;
        this.notebookRowMapper = notebookRowMapper;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_PHONE, userRowMapper, phone));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }    }

    @Override
    public Optional<User> findByCookieToken(String cookieToken) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_COOKIE_TOKEN, userRowMapper, cookieToken));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> setCookieToken(Long id, String cookieToken) {
        try {
            int affectedRows = jdbcTemplate.update(
                    SQL_SET_COOKIE_TOKEN,
                    cookieToken,
                    id);

            if (affectedRows > 0) {
                return findById(id);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> setCookieToken(String email, String cookieToken) {
        try {
            int affectedRows = jdbcTemplate.update(
                    SQL_SET_COOKIE_TOKEN_BY_EMAIL,
                    cookieToken,
                    email);

            if (affectedRows > 0) {
                return findByEmail(email);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> save(User user) {
        try {
            User savedUser = jdbcTemplate.queryForObject(SQL_SAVE, userRowMapper,
                    user.getNickname(),
                    user.getEmail(),
                    user.getPhone(), //TODO
                    user.getHashPassword(),
                    user.getBirthdate());
            //todo
            return Optional.of(savedUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(User user) {
        return jdbcTemplate.update(SQL_UPDATE, user.getNickname(), user.getEmail(), user.getHashPassword(), user.getId()) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) > 0;
    }





    @Override
    public List<Notebook> getNotebooksById(Long userId) {
        return jdbcTemplate.query(SQL_GET_NOTEBOOKS_BY_USER_ID, notebookRowMapper, userId);
    }

    @Override
    public List<Notebook> getNotebooksById(Long userId, Long start, Integer step) {
        if (step == null) step=6;
        return jdbcTemplate.query(SQL_GET_NOTEBOOKS_BY_USER_ID_WITH_LIMIT, notebookRowMapper, userId, start, step);
    }

    @Override
    public Optional<User> setImageId(Long id, String imageId) {
        try {
            int affectedRows = jdbcTemplate.update(
                    SQL_SET_IMAGE_ID_BY_ID,
                    imageId,
                    id);

            if (affectedRows > 0) {
                return findById(id);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
