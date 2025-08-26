package com.midlane.project_management_tool_project_service.model;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "team_projects")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;  // comes from UserService
    private Long projectId;  // FK to Project

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", insertable = false, updatable = false)
    private Project project;
}
