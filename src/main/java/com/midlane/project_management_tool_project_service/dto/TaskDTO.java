package com.midlane.project_management_tool_project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private Long projectId;
    private Long sprintId;
    private String title;
    private String description;
    private String assignee;
    private String reporter;
    private String dueDate; // Use ISO format (e.g., "2025-08-08T00:00:00Z")
    private String epic;
    private String priority; // Highest | High | Medium | Low | Lowest
    private String status;   // Backlog | Todo | In Progress | Review | Done
    private String type;     // Story | Bug | Task | Epic
    private Integer storyPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> labels;
    private List<CommentDTO> comments;
}
