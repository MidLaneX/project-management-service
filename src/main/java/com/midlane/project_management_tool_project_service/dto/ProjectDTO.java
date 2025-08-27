package com.midlane.project_management_tool_project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;
    private Long orgId;
    private String name;
    private String type;
    private String templateType;
    private List<String> features;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;



    public ProjectDTO(Long id, String name, String templateType, List<String> features) {
        this.id = id;
        this.name = name;
        this.templateType = templateType;
        this.features = features;
    }
}
