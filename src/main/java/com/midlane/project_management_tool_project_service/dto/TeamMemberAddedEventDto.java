package com.midlane.project_management_tool_project_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberAddedEventDto {
    private Long userId;
    private Long organizationId;
    private Long teamId;
    private String role;
    private LocalDateTime timestamp;
    private String eventType;
    private String teamName;
    private String organizationName;
    private String userEmail;
    private String userName;
}
