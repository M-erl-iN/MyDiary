package ru.itis.mydiary.repositories;

import ru.itis.mydiary.dto.InvitePost;
import ru.itis.mydiary.entity.Invite;

import java.util.List;
import java.util.Optional;

public interface InviteRepository {
    List<Invite> findAll();
    Optional<Invite> findById(Long id);
    List<Invite> findByUserSendId(Long userSendId);
    List<Invite> findByUserInviteId(Long userInviteId);
    Optional<Invite> save(Invite invite);
    boolean update(Invite invite);
    boolean deleteById(Long id);
    boolean accept(Invite invite);
}
