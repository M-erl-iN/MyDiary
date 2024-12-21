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
public class RoleInteraction {
    private Long id;
    private Long userId;
    private Long notebookId;
    private Long roleId;
    private Date appendDate;
}
