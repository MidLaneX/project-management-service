package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "role")
    private String role; // ADMIN / MEMBER etc.
}
