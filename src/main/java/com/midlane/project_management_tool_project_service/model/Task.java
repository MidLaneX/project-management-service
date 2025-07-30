package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @ManyToOne
        @JoinColumn(name = "feature_id")
        private Feature feature;

        public Task() {}
        public Task(String name, Feature feature) {
            this.name = name;
            this.feature = feature;



        }


    // Getters & setters

    public Long getId() { return id; }

    public String getName() { return name; }

    public Feature getFeature() { return feature; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setFeature(Feature feature) { this.feature = feature; }
}
