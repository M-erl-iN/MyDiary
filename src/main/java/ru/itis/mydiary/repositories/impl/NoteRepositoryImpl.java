package ru.itis.mydiary.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.mydiary.entity.Note;
import ru.itis.mydiary.mapper.NoteRowMapper;
import ru.itis.mydiary.repositories.NoteRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class NoteRepositoryImpl implements NoteRepository {
    private final NoteRowMapper noteRowMapper;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_ALL = "SELECT * FROM notes";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM notes WHERE id = ?";
    private static final String SQL_FIND_BY_NOTEBOOK_ID = "SELECT * FROM notes WHERE notebook_id = ?";
    private static final String SQL_FIND_BY_NOTEBOOK_ID_AND_CREATOR_ID = "SELECT * FROM notes WHERE notebook_id = ?  AND creator_id = ?";
    private static final String SQL_SAVE = "INSERT INTO notes (title, text, creator_id, notebook_id) VALUES (?, ?, ?, ?) returning *";
    private static final String SQL_FIND_BY_NOTEBOOK_ID_AND_TITLE = "SELECT * FROM notes WHERE notebook_id = ? AND title = ?";
    private static final String SQL_UPDATE = "UPDATE notes SET title = ?, text = ?, creator_id = ?, notebook_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM notes WHERE id = ?";
    private static final String SQL_DELETE_BY_CREATOR_ID = "DELETE FROM notes WHERE creator_id = ?";
    private static final String SQL_GET_NOTES_BY_ID = "select * from notes where notebook_id = ?";
    private static final String SQL_GET_NOTES_BY_ID_WITH_LIMIT = "select * from notes where notebook_id = ? offset ? limit ?";



    public NoteRepositoryImpl(DataSource dataSource, NoteRowMapper noteRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.noteRowMapper = noteRowMapper;
    }

    @Override
    public List<Note> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, noteRowMapper);
    }

    @Override
    public Optional<Note> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, noteRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Note> findByNotebookId(Long notebookId) {
        return jdbcTemplate.query(SQL_FIND_BY_NOTEBOOK_ID, noteRowMapper, notebookId);
    }

    @Override
    public List<Note> findInNotebookByCreatorId(Long notebookId, Long creatorId) {
        return jdbcTemplate.query(SQL_FIND_BY_NOTEBOOK_ID_AND_CREATOR_ID, noteRowMapper, notebookId, creatorId);
    }

    @Override
    public List<Note> findByCreatorIdAndTitle(Long creatorId, String title) {
        return jdbcTemplate.query(SQL_FIND_BY_NOTEBOOK_ID_AND_TITLE, noteRowMapper, creatorId, title);
    }

    @Override
    public Optional<Note> save(Note note) {
        try {
            Note savedNote = jdbcTemplate.queryForObject(SQL_SAVE, noteRowMapper,
                    note.getTitle(),
                    note.getText(),
                    note.getCreatorId(),
                    note.getNotebookId());
            //todo
            return Optional.of(savedNote);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Note note) {
        return jdbcTemplate.update(SQL_UPDATE,
                note.getTitle(),
                note.getText(),
                note.getCreatorId(),
                note.getNotebookId(),
                note.getId()
        ) > 0;
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
    public List<Note> getNotesByNotebookId(Long notebookId) {
        return jdbcTemplate.query(SQL_GET_NOTES_BY_ID, noteRowMapper, notebookId);
    }

    @Override
    public List<Note> getNotesByNotebookId(Long notebookId, Long start, Integer step) {
        if (step == null) step=6;
        return jdbcTemplate.query(SQL_GET_NOTES_BY_ID_WITH_LIMIT, noteRowMapper, notebookId, start, step);
    }
}
