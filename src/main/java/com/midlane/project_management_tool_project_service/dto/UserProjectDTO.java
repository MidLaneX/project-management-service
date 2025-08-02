package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;


@Data
public class UserProjectDTO {
    private Long id;
    private Long projectId;
    private Long userId;
    private String role; // Optional: ADMIN, MEMBER, etc.



}
