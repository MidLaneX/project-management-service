package com.midlane.project_management_tool_project_service.dto;

import java.util.Set;

public class ProjectResponseDTO {

    private Long id;
    private String name;
    private String projectType;
    private String templateName;
    private Set<FeatureResponseDTO> features;

    public ProjectResponseDTO(Long id, String name, String projectType,
                              String templateName, Set<FeatureResponseDTO> features) {
        this.id = id;
        this.name = name;
        this.projectType = projectType;
        this.templateName = templateName;
        this.features = features;
    }

    // Getters

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getProjectType() { return projectType; }
    public String getTemplateName() { return templateName; }
    public Set<FeatureResponseDTO> getFeatures() { return features; }
}
