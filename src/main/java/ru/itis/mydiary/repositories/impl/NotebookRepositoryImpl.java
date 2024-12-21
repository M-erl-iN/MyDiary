package ru.itis.mydiary.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;
import ru.itis.mydiary.mapper.NoteRowMapper;
import ru.itis.mydiary.mapper.NotebookRowMapper;
import ru.itis.mydiary.mapper.UserRowMapper;
import ru.itis.mydiary.repositories.NotebookRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class NotebookRepositoryImpl implements NotebookRepository {
    private final NotebookRowMapper notebookRowMapper;
    private final UserRowMapper userRowMapper;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_ALL = "SELECT * FROM notebooks";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM notebooks where id = ?";
    private static final String SQL_FIND_BY_CREATOR_ID = "SELECT * FROM notebooks where creator_id = ?";
    private static final String SQL_FIND_BY_CREATOR_ID_AND_TITLE = "SELECT * FROM notebooks where creator_id = ? AND title = ?";
    private static final String SQL_SAVE = "INSERT INTO notebooks (title, description, creator_id) VALUES (?, ?, ?) returning *";
    private static final String SQL_SAVE_ = "insert into users_notebooks_roles (user_id, notebook_id, role_id) values (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE notebooks SET title = ?, description = ?, creator_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM notebooks WHERE id = ?";
    private static final String SQL_DELETE_BY_CREATOR_ID = "DELETE FROM notebooks WHERE creator_id = ?";


    private static final String SQL_GET_USERS_BY_ID =
            "select * from users where id in (    select user_id from users_notebooks_roles where notebook_id = ?    )";


    public NotebookRepositoryImpl(DataSource dataSource, NotebookRowMapper notebookRowMapper, UserRowMapper userRowMapper, NoteRowMapper noteRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.notebookRowMapper = notebookRowMapper;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<Notebook> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, notebookRowMapper);
    }

    @Override
    public Optional<Notebook> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, notebookRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Notebook> findByCreatorId(Long creatorId) {
        return jdbcTemplate.query(SQL_FIND_BY_CREATOR_ID, notebookRowMapper, creatorId);
    }

    @Override
    public List<Notebook> findByCreatorIdAndTitle(Long creatorId, String title) {
        return jdbcTemplate.query(SQL_FIND_BY_CREATOR_ID_AND_TITLE, notebookRowMapper, creatorId, title);
    }

    @Override
    public Optional<Notebook> save(Notebook notebook) {
        try {
            Notebook savedNotebook = jdbcTemplate.queryForObject(SQL_SAVE, notebookRowMapper,
                    notebook.getTitle(),
                    notebook.getDescription(),
                    notebook.getCreatorId());

            int affectedRows = jdbcTemplate.update(SQL_SAVE_,
                    savedNotebook.getCreatorId(),
                    savedNotebook.getId(), 1);
            //todo
            if (affectedRows == 1) {
                return Optional.of(savedNotebook);
            } return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Notebook notebook) {
        return jdbcTemplate.update(SQL_UPDATE,
                notebook.getTitle(),
                notebook.getDescription(),
                notebook.getCreatorId(),
                notebook.getId()) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) > 0;
    }

    @Override
    public boolean deleteByCreatorId(Long creatorId) {
        return jdbcTemplate.update(SQL_DELETE_BY_CREATOR_ID) > 0;
    }


    @Override
    public List<User> getUserById(Long notebookId) {
        return jdbcTemplate.query(SQL_GET_USERS_BY_ID, userRowMapper, notebookId);
    }
}
