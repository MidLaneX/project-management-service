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

    @GetMapping("/{projectId}/{templateType}")
    public ResponseEntity<ProjectDTO> getProjectByID(@PathVariable Long projectId,@PathVariable String templateType) {
        return ResponseEntity.ok(projectService.getProject(projectId,templateType));
    }
    @GetMapping("/user-projects")
    public ResponseEntity<List<ProjectDTO>> getProjectsForUser(
            @RequestParam Long userId,
            @RequestParam Long orgId,
            @RequestParam String role, // from UserService
            @RequestParam(required = false) List<Long> teamIds,
            @RequestParam String templateType) {

        return ResponseEntity.ok(
                projectService.getProjectsForUser(userId, orgId, role, teamIds, templateType)
        );
    }


//    @PostMapping("/createUserOfProjects")
//    public ResponseEntity<UserProjectDTO> createUserProjects(@RequestBody CreateUserProjectRequestDTO requestDTO) {
//        return  ResponseEntity.ok(projectService.createUserProject(requestDTO.getProjectDTO(),requestDTO.getUserProjectRequestDTO()));
//    }
//
//    @GetMapping("/usersOfProject")
//    public ResponseEntity<List<UserProjectDTO>> getUserProject(@RequestParam ProjectDTO projectDTO) {
////        ProjectDTO requestDTO = new ProjectDTO();
////        requestDTO.setId(id);
////        requestDTO.setTemplateType(templateType);
//        return ResponseEntity.ok(projectService.getUsersOfProject(projectDTO));
//    }
//
//
//    @GetMapping("/projectsOfUser")
//    public ResponseEntity<List<ProjectDTO>> getProjectsOfUser(@RequestParam Long userId ,@RequestParam String templateType) {
//        return ResponseEntity.ok(projectService.getProjectsOfUser(userId, templateType));
//    }





    @PostMapping("/{projectId}/stories")
    public ResponseEntity<TaskDTO> createStory(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO,@PathVariable String templateType) {
        return ResponseEntity.ok(projectService.createStory(projectId, taskDTO,templateType));
    }
//    @GetMapping("/{projectId}/stories")
//    public ResponseEntity<TaskDTO> getStory(@RequestBody ProjectDTO dto) {
//        return ResponseEntity.ok(projectService.getStory(dto));
//    }
// Update project
@PutMapping("/{projectId}/{templateType}")
public ResponseEntity<ProjectDTO> updateProject(
        @PathVariable Long projectId,
        @PathVariable String templateType,
        @RequestBody ProjectDTO dto) {
    return ResponseEntity.ok(projectService.updateProject(projectId, templateType, dto));
}

    // Delete project
    @DeleteMapping("/{projectId}/{templateType}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId,
            @PathVariable String templateType) {
        projectService.deleteProject(projectId, templateType);
        return ResponseEntity.noContent().build();
    }

}
