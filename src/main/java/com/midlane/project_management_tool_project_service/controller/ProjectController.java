// ProjectController.java
package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.UserProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.dto.StoryDTO;
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

    @PostMapping("/userprojects")
    public ResponseEntity<UserProjectDTO> getUserProjects(@RequestBody ProjectDTO dto,@RequestBody UserProjectDTO userProjectDTO) {
        return  ResponseEntity.ok(projectService.createUserProject(dto,userProjectDTO));
    }

    @GetMapping("/userprojects")
    public ResponseEntity<List<UserProjectDTO>> getUserProject(@RequestBody ProjectDTO dto) {
        return  ResponseEntity.ok(Collections.singletonList(projectService.getUserProject(dto)));
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
