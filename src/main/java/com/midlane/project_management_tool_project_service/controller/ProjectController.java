package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.model.Feature;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {


    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO request) {
        Project project = projectService.createProject(request.getName(), request.getProjectType(), request.getTemplateName());

        Set<FeatureResponseDTO> featureDTOs = project.getTemplate().getFeatures().stream().map(feature ->
                new FeatureResponseDTO(
                        feature.getId(),
                        feature.getName(),
                        feature.getTasks().stream()
                                .map(task -> new TaskResponseDTO(task.getId(), task.getName()))
                                .collect(Collectors.toSet())
                )
        ).collect(Collectors.toSet());

        ProjectResponseDTO response = new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getProjectType(),
                project.getTemplate().getName(),
                featureDTOs
        );

        return ResponseEntity.ok(response);
    }
}