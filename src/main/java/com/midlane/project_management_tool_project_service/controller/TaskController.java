package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.TaskDTO;
import com.midlane.project_management_tool_project_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @PathVariable Long projectId,
            @RequestBody TaskDTO dto,
            @RequestParam(defaultValue = "scrum") String templateType
    ) {
        return ResponseEntity.ok(taskService.createTask(projectId, dto, templateType));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "scrum") String templateType
    ) {
        return ResponseEntity.ok(taskService.getTasksByProjectId(projectId, templateType));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "scrum") String templateType
    ) {
        return ResponseEntity.ok(taskService.getTaskById(taskId, templateType));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskDTO dto,
            @RequestParam(defaultValue = "scrum") String templateType
    ) {
        return ResponseEntity.ok(taskService.updateTask(taskId, dto, templateType));
    }

    @PutMapping("/{taskId}/{sprintId}")
        public ResponseEntity<TaskDTO> updateSprint(

                @PathVariable Long taskId,
                @PathVariable Long sprintId,
                @RequestParam(defaultValue = "scrum") String templateType
        ) {
            return ResponseEntity.ok(taskService.updateSprint(taskId, sprintId, templateType));
        }




    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "scrum") String templateType
    ) {
        taskService.deleteTask(taskId, templateType);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskStatusRequest statusRequest,
            @RequestParam(defaultValue = "scrum") String templateType
    ) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, statusRequest.getStatus(), templateType));
    }



    // DTO to accept status body
    public static class TaskStatusRequest {
        private String status;
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }
}
