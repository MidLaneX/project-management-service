package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "features")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Backlog, Board, etc.

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    // Constructors, getters, setters

    public Feature() {}

    public Feature(String name, Template template) {
        this.name = name;
        this.template = template;
    }

    // Getters & setters

    public Long getId() { return id; }

    public String getName() { return name; }

    public Template getTemplate() { return template; }

    public Set<Task> getTasks() { return tasks; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setTemplate(Template template) { this.template = template; }

    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
}
