package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;

@Data
public class UserAddedToTeamEvent {
    private Long userId;
    private Long teamId;
    private Long orgId;
    private String role;
}
