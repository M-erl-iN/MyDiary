package ru.itis.mydiary.repositories;

import ru.itis.mydiary.entity.Role;
import ru.itis.mydiary.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface RoleInteractionRepository {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> save(Role role);
    boolean update(Role role);
    boolean deleteById(Long id);

    HashMap<User, Role> getUsersIdAndRolesIdByNotebookId(Long id); //userid roleid
    Optional<Role> getRoleByUserIdAndNoteBookId(Long userId, Long notebookId);

    List<Long> getUsersIdByNotebookId(Long notebookId);
    boolean deleteByUserIdAndNotebookId(Long userId, Long notebookId);

//    List<User> findByRoleInNotebook(Long notebookId, Role role);
//    List<Notebook>

}
