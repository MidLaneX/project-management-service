package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String projectType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    // Constructors, getters, setters

    public Project() {}

    public Project(String name, String projectType, Template template) {
        this.name = name;
        this.projectType = projectType;
        this.template = template;
    }

    // Getters & setters

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getProjectType() { return projectType; }

    public Template getTemplate() { return template; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setProjectType(String projectType) { this.projectType = projectType; }

    public void setTemplate(Template template) { this.template = template; }
}
