package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
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

class ProjectServiceTest {

    @Mock
    private TemplateFactory templateFactory;

    @Mock
    private Template template;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProject_shouldReturnDTO() {
        ProjectDTO dto = new ProjectDTO();
        when(templateFactory.getTemplate("scrum")).thenReturn(template);
        when(template.createProject(dto)).thenReturn(dto);

        ProjectDTO result = projectService.createProject(dto, "scrum");

        assertThat(result).isEqualTo(dto);
        verify(templateFactory).getTemplate("scrum");
        verify(template).createProject(dto);
    }

    @Test
    void getProjectsForUser_shouldCallTemplate() {
        when(templateFactory.getTemplate("scrum")).thenReturn(template);
        when(template.getProjectsForUser(1L, 1L)).thenReturn(List.of(new ProjectDTO()));

        var result = projectService.getProjectsForUser(1L, 1L, "scrum");

        assertThat(result).hasSize(1);
        verify(templateFactory).getTemplate("scrum");
        verify(template).getProjectsForUser(1L, 1L);
    }
}
