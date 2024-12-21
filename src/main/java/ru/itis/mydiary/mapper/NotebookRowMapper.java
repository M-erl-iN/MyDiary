package ru.itis.mydiary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.mydiary.dto.CreateNotebookDto;
import ru.itis.mydiary.entity.Notebook;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotebookRowMapper implements RowMapper<Notebook> {

    @Override
    public Notebook mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Notebook.builder()
                .id((Long) rs.getObject("id"))
                .title((String) rs.getObject("title"))
                .description((String) rs.getObject("description"))
                .creatorId((Long) rs.getObject("creator_id"))
                .creationDate((Date) rs.getObject("creation_date"))
                .build();
    }

    public Notebook toEntity(CreateNotebookDto notebookDto, Long creatorId) {
        return Notebook.builder()
                .title(notebookDto.getTitle())
                .description(notebookDto.getDescription())
                .creatorId(creatorId)
                .build();
    }
}
