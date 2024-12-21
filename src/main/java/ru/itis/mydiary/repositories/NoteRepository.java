package ru.itis.mydiary.repositories;

import ru.itis.mydiary.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    List<Note> findAll();
    Optional<Note> findById(Long id);
    List<Note> findByNotebookId(Long notebookId);
    List<Note> findInNotebookByCreatorId(Long notebookId, Long creatorId);
    List<Note> findByCreatorIdAndTitle(Long creatorId, String title);
    Optional<Note> save(Note note);
    boolean update(Note note);
    boolean deleteById(Long id);

    boolean deleteByCreatorId(Long creatorId);

    List<Note> getNotesByNotebookId(Long notebookId);

    List<Note> getNotesByNotebookId(Long notebookId, Long start, Integer step);
}
