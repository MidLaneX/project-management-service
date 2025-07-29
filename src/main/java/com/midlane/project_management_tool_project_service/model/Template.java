package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Scrum, Kanban

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Feature> features;

    // Constructors, getters, setters

    public Template() {}

    public Template(String name) {
        this.name = name;
    }

    // Getters & setters

    public Long getId() { return id; }

    public String getName() { return name; }

    public Set<Feature> getFeatures() { return features; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setFeatures(Set<Feature> features) { this.features = features; }
}
