package ru.itis.mydiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitePost {
    private Long inviteId;
    private String userSendName;
    private String userInviteName;
    private String notebookTitle;
    private String roleName;
}