package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.TaskDTO;
import com.midlane.project_management_tool_project_service.template.Template;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TemplateFactory templateFactory;

    @Mock
    private Template template;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldReturnDTO() {
        TaskDTO dto = new TaskDTO();
        when(templateFactory.getTemplate("scrum")).thenReturn(template);
        when(template.createTask(1L, dto)).thenReturn(dto);

        TaskDTO result = taskService.createTask(1L, dto, "scrum");

        assertThat(result).isEqualTo(dto);
        verify(templateFactory).getTemplate("scrum");
        verify(template).createTask(1L, dto);
    }

    @Test
    void getTasksByProjectId_shouldReturnList() {
        TaskDTO dto = new TaskDTO();
        when(templateFactory.getTemplate("scrum")).thenReturn(template);
        when(template.getTasksByProject(1L)).thenReturn(List.of(dto));

        List<TaskDTO> result = taskService.getTasksByProjectId(1L, "scrum");

        assertThat(result).hasSize(1);
        verify(templateFactory).getTemplate("scrum");
        verify(template).getTasksByProject(1L);
    }

    @Test
    void updateTaskStatus_shouldCallTemplate() {
        TaskDTO dto = new TaskDTO();
        when(templateFactory.getTemplate("scrum")).thenReturn(template);
        when(template.updateTaskStatus(1L, "DONE")).thenReturn(dto);

        TaskDTO result = taskService.updateTaskStatus(1L, "DONE", "scrum");

        assertThat(result).isEqualTo(dto);
        verify(templateFactory).getTemplate("scrum");
        verify(template).updateTaskStatus(1L, "DONE");
    }
}
