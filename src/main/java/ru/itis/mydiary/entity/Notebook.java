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
public class Notebook {
    private long id;
    private String title;
    private String description;
    private long creatorId;
    private Date creationDate = new Date(System.currentTimeMillis());
}