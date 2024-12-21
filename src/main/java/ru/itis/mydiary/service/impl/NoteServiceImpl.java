package ru.itis.mydiary.service.impl;

import lombok.AllArgsConstructor;
import ru.itis.mydiary.dto.CreateNoteDto;
import ru.itis.mydiary.dto.CreateNoteResponse;
import ru.itis.mydiary.entity.Note;
import ru.itis.mydiary.mapper.NoteRowMapper;
import ru.itis.mydiary.repositories.NoteRepository;
import ru.itis.mydiary.service.NoteService;

import java.util.Optional;

@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteRowMapper noteRowMapper;

    @Override
    public CreateNoteResponse createNote(CreateNoteDto note, Long creatorId, Long notebookId) {
        if (note.getTitle() == null) {
            return response(1, "Empty data", null);}

        Note temp = noteRowMapper.toEntity(note, creatorId, notebookId);
        Optional<Note> noteOptional = noteRepository.save(temp);
        if (noteOptional.isEmpty()) {
            return response(99, "Database process error", null);}

        return response(0, "OK", noteOptional.get());

    }

    private CreateNoteResponse response(int status, String statusDesc, Note note) {
        return CreateNoteResponse.builder()
                .status(status)
                .statusDesc(statusDesc)
                .note(note)
                .build();
    }
}
