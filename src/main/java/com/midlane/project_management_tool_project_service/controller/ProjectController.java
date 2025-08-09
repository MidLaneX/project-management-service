// ProjectController.java
package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO dto, @RequestParam(defaultValue = "scrum") String template) {
        return ResponseEntity.ok(projectService.createProject(dto, template));
    }

    @GetMapping
    public ResponseEntity<ProjectDTO> getProjectByID(@RequestBody ProjectDTO dto) {
         return ResponseEntity.ok(projectService.getProject(dto));
    }

    @PostMapping("/createUserProjects")
    public ResponseEntity<UserProjectDTO> createUserProjects(@RequestBody CreateUserProjectRequestDTO requestDTO) {
        return  ResponseEntity.ok(projectService.createUserProject(requestDTO.getProjectDTO(),requestDTO.getUserProjectRequestDTO()));
    }

    @GetMapping("/userProjects")
    public ResponseEntity<List<UserProjectDTO>> getUserProject(@RequestParam Long id, @RequestParam String templateType) {
        ProjectDTO requestDTO = new ProjectDTO();
        requestDTO.setId(id);
        requestDTO.setTemplateType(templateType);
        return ResponseEntity.ok(projectService.getUsersOfProject(requestDTO));
    }


    @GetMapping("/projectsOfUser")
    public ResponseEntity<List<ProjectDTO>> getProjectsOfUser(@RequestBody CreateUserProjectRequestDTO requestDTO) {
        return ResponseEntity.ok(Collections.singletonList( (ProjectDTO) projectService.getProjectsOfUser(requestDTO.getProjectDTO(), requestDTO.getUserProjectRequestDTO())));
    }

    @PostMapping("/{projectId}/sprints")
    public ResponseEntity<SprintDTO> createSprint(@PathVariable Long projectId, @RequestBody SprintDTO sprintDTO) {
        return ResponseEntity.ok(projectService.createSprint(projectId, sprintDTO));
    }

    @GetMapping("/{projectId}/sprints")
    public ResponseEntity<SprintDTO> getSprint(@RequestBody ProjectDTO dto) {
        return  ResponseEntity.ok(projectService.getSprint(dto));
    }

    @PostMapping("/{projectId}/stories")
    public ResponseEntity<StoryDTO> createStory(@PathVariable Long projectId, @RequestBody StoryDTO storyDTO) {
        return ResponseEntity.ok(projectService.createStory(projectId, storyDTO));
    }
    @GetMapping("/{projectId}/stories")
    public ResponseEntity<StoryDTO> getStory(@RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.getStory(dto));
    }
}
