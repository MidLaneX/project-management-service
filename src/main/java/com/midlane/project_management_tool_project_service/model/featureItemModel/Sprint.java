package com.midlane.project_management_tool_project_service.model.featureItemModel;

import com.midlane.project_management_tool_project_service.model.Project;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "sprints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId; //

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status; // Planned, Active, Closed

    public  Long getProjectId() {
        return projectId;
    }
}
