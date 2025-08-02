package com.midlane.project_management_tool_project_service.model.featureItemModel;

import com.midlane.project_management_tool_project_service.model.Project;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "stories")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "project_id", nullable = false)
    private Long projectId;


    @JoinColumn(name = "sprint_id")
    private Long sprintId; // nullable: null means backlog

    private String title;

    private String description;

    private String status; // To Do, In Progress, Done

    private Integer storyPoints;
}
