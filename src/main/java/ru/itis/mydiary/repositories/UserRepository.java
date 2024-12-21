package ru.itis.mydiary.repositories;

import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByCookieToken(String cookieToken);
    Optional<User> setCookieToken(Long id, String cookieToken); //update

    Optional<User> setCookieToken(String email, String cookieToken);

    Optional<User> save(User user);
    boolean update(User user);
    boolean deleteById(Long id);

    List<Notebook> getNotebooksById(Long userId);

    List<Notebook> getNotebooksById(Long userId, Long start, Integer step);

    Optional<User> setImageId(Long id, String imageId);
}
