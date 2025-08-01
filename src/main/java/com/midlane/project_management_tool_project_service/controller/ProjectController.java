package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.model.Feature;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestParam String name, @RequestParam String template) {
        return ResponseEntity.ok(projectService.createProject(name, template));
    }
}
