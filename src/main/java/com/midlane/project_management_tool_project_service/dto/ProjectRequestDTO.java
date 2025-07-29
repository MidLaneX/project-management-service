package com.midlane.project_management_tool_project_service.dto;

public class ProjectRequestDTO {

    private String name;
    private String projectType;
    private String templateName;

    // Getters & setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
}
