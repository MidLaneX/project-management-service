package com.midlane.project_management_tool_project_service.dto;

import java.util.Set;

public class FeatureResponseDTO {

    private Long id;
    private String name;
    private Set<TaskResponseDTO> tasks;

    public FeatureResponseDTO(Long id, String name, Set<TaskResponseDTO> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    // Getters

    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<TaskResponseDTO> getTasks() { return tasks; }
}
