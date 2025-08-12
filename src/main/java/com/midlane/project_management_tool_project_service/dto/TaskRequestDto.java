package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TaskRequestDto {
    private Long id;
    private Long projectId;
    private Long sprintId;

}
