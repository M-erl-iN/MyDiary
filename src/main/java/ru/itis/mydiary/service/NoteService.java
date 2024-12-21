package ru.itis.mydiary.service;

import ru.itis.mydiary.dto.CreateNoteDto;
import ru.itis.mydiary.dto.CreateNoteResponse;

public interface NoteService {
    CreateNoteResponse createNote(CreateNoteDto note, Long creatorId, Long notebookId);
}
