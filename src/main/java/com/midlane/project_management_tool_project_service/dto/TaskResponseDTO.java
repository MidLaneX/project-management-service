package com.midlane.project_management_tool_project_service.dto;

public class TaskResponseDTO {

    private Long id;
    private String name;

    public TaskResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters

    public Long getId() { return id; }
    public String getName() { return name; }
}
