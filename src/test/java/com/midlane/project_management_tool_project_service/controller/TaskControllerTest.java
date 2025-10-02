package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.TaskDTO;
import com.midlane.project_management_tool_project_service.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnTask() {
        TaskDTO dto = new TaskDTO();
        when(taskService.createTask(1L, dto, "scrum")).thenReturn(dto);

        var response = taskController.createTask(1L, dto, "scrum");

        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        when(taskService.getTasksByProjectId(1L, "scrum")).thenReturn(List.of(new TaskDTO()));

        var response = taskController.getAllTasks(1L, "scrum");

        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void updateTaskStatus_ShouldCallService() {
        TaskDTO dto = new TaskDTO();
        when(taskService.updateTaskStatus(1L, "DONE", "scrum")).thenReturn(dto);

        TaskController.TaskStatusRequest req = new TaskController.TaskStatusRequest();
        req.setStatus("DONE");

        var response = taskController.updateTaskStatus(1L, 1L, req, "scrum");

        assertThat(response.getBody()).isEqualTo(dto);
    }
}
