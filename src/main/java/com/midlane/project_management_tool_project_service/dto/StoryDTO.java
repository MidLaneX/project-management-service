// StoryDTO.java
package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;

@Data
public class StoryDTO {
    private Long id;
    private Long projectId;
    private Long sprintId;
    private String title;
    private String description;
    private String status;
    private Integer storyPoints;
}
