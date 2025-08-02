// ProjectDTO.java
package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectDTO {


    private Long id;
    private String userId;
    private String name;
    private String templateType;
    private List<String> features;



}