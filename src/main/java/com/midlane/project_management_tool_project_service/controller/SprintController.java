package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    // Create sprint
    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(
            @PathVariable Long projectId,
            @RequestBody SprintDTO sprintDTO,
            @RequestParam String template
    ) {
        SprintDTO createdSprint = sprintService.createSprint(projectId, sprintDTO, template);
        return ResponseEntity.ok(createdSprint);
    }

    // Get latest sprint
    @GetMapping("/latest")
    public ResponseEntity<SprintDTO> getLatestSprint(
            @PathVariable Long projectId,
            @RequestParam String template
    ) {
        SprintDTO sprint = sprintService.getSprint(projectId, template);
        return ResponseEntity.ok(sprint);
    }

    // Get all sprints
    @GetMapping
    public ResponseEntity<List<SprintDTO>> getAllSprints(
            @PathVariable Long projectId,
            @RequestParam String template
    ) {
        List<SprintDTO> sprints = sprintService.getAllSprints(projectId, template);
        return ResponseEntity.ok(sprints);
    }

    // Update sprint
    @PutMapping("/{sprintId}")
    public ResponseEntity<SprintDTO> updateSprint(
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            @RequestBody SprintDTO sprintDTO,
            @RequestParam String template
    ) {
        SprintDTO updatedSprint = sprintService.updateSprint(projectId, sprintId, sprintDTO, template);
        return ResponseEntity.ok(updatedSprint);
    }

    // Delete sprint
    @DeleteMapping("/{sprintId}")
    public ResponseEntity<Void> deleteSprint(
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            @RequestParam String template
    ) {
        sprintService.deleteSprint(projectId, sprintId, template);
        return ResponseEntity.noContent().build();
    }
}
