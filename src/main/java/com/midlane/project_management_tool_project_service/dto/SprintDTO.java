// SprintDTO.java
package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SprintDTO {
    private Long id;
    private Long projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goal;
    private String status;

    public SprintDTO(Long id, Long projectId, String name, LocalDate startDate, LocalDate endDate,String goal, String status ) {
                this.id = id;
                this.projectId = projectId;
                this.name = name;
                this.startDate = startDate;
                this.endDate = endDate;
                this.goal = goal;
                this.status = status;




    }
}
