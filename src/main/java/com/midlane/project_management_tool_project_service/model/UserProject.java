package com.midlane.project_management_tool_project_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
@Entity
@Table(name = "project_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String role; // Optional: ADMIN, MEMBER, etc.

}
