// ProjectDTO.java
package com.midlane.project_management_tool_project_service.dto;

import lombok.Data;
import java.util.List;
import jdk.jshell.Snippet;
import lombok.Data;

import lombok.Builder;
import lombok.Data;
import lombok.Data;
@Data
public class ProjectDTO {


    private Long id;
    private Long userId;
    private String name;
    private String templateType;
    private List<String> features;


}