package ru.itis.mydiary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invite {
    private Long id;
    private Long userSendId;
    private Long userInviteId;
    private Long notebookId;
    private Long roleId;
}
