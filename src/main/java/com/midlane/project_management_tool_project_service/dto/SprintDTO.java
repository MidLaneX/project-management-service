// SprintDTO.java
package com.midlane.project_management_tool_project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintDTO {
    private Long id;
    private Long projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goal;
    private String status;

 // added alarg instead using build.


}
