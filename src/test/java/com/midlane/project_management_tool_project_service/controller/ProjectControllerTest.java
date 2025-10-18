package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.TaskDTO;
import com.midlane.project_management_tool_project_service.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProject_ShouldReturnProjectDTO() {
        ProjectDTO dto = new ProjectDTO();
        when(projectService.createProject(dto, "scrum")).thenReturn(dto);

        var response = projectController.createProject(dto, "scrum");

        assertThat(response.getBody()).isEqualTo(dto);
        verify(projectService).createProject(dto, "scrum");
    }

    @Test
    void getProjectsForUser_ShouldReturnList() {
        ProjectDTO dto = new ProjectDTO();
        when(projectService.getProjectsForUser(1L, 1L, "scrum")).thenReturn(List.of(dto));

        var response = projectController.getProjectsForUser(1L, 1L, "scrum");

        assertThat(response.getBody()).hasSize(1);
    }


}
