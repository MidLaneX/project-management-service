package com.midlane.project_management_tool_project_service.controller;

import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.service.SprintService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SprintControllerTest {

    @Mock
    private SprintService sprintService;

    @InjectMocks
    private SprintController sprintController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSprint_ShouldReturnSprintDTO() {
        SprintDTO dto = new SprintDTO();
        when(sprintService.createSprint(1L, dto, "scrum")).thenReturn(dto);

        var response = sprintController.createSprint(1L, dto, "scrum");

        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    void getAllSprints_ShouldReturnList() {
        when(sprintService.getAllSprints(1L, "scrum")).thenReturn(List.of(new SprintDTO()));

        var response = sprintController.getAllSprints(1L, "scrum");

        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void deleteSprint_ShouldCallService() {
        sprintController.deleteSprint(1L, 1L, "scrum");

        verify(sprintService).deleteSprint(1L, 1L, "scrum");
    }
}
