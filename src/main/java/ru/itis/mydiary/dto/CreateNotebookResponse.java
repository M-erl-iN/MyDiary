package ru.itis.mydiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.mydiary.entity.Notebook;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotebookResponse {
    private int status;
    private String statusDesc;
    private Notebook notebook;
}
