package ru.itis.mydiary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.mydiary.dto.CreateNoteDto;
import ru.itis.mydiary.entity.Note;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRowMapper implements RowMapper<Note> {
    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Note.builder()
                .id((Long) rs.getObject("id"))
                .title((String) rs.getObject("title"))
                .text((String) rs.getObject("text"))
                .creationDate((Date) rs.getObject("creation_date"))
                .creatorId((Long) rs.getObject("creator_id"))
                .notebookId((Long) rs.getObject("notebook_id"))
                .build();
    }

    public Note toEntity(CreateNoteDto note, Long creatorId, Long notebookId) {
        return Note.builder()
                .title(note.getTitle())
                .creatorId(creatorId)
                .notebookId(notebookId)
                .build();
    }
}
