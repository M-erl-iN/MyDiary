package ru.itis.mydiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.mydiary.entity.Note;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteResponse {
    private int status;
    private String statusDesc;
    private Note note;
}
