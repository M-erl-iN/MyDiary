package ru.itis.mydiary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private long id;
    private String title;
    private String text;
    private Date creationDate;
    private long creatorId;
    private long notebookId;
}