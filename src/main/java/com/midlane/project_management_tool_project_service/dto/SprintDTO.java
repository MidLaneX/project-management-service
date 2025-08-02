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
    private String status;
}
