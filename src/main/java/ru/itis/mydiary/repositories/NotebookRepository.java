package ru.itis.mydiary.repositories;

import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;

import java.util.List;
import java.util.Optional;

public interface NotebookRepository {
    List<Notebook> findAll();
    Optional<Notebook> findById(Long id);
    List<Notebook> findByCreatorId(Long creatorId);
    List<Notebook> findByCreatorIdAndTitle(Long creatorId, String title);
    Optional<Notebook> save(Notebook notebook);
    boolean update(Notebook notebook);
    boolean deleteById(Long id);
    boolean deleteByCreatorId(Long creatorId);

    List<User> getUserById(Long notebookId);
}
