package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.template.SprintCapableTemplate;
import com.midlane.project_management_tool_project_service.template.Template;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SprintServiceTest {

    @Mock
    private TemplateFactory templateFactory;

    @Mock
    private SprintCapableTemplate sprintTemplate;

    @InjectMocks
    private SprintService sprintService;

    private SprintDTO sprintDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sprintDTO = SprintDTO.builder()
                .id(1L)
                .projectId(1L)
                .name("Sprint 1")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(14))
                .goal("Goal")
                .status("ACTIVE")
                .build();
    }

    @Test
    void createSprint_shouldReturnDTO() {
        // STEP 1: Mock TemplateFactory to return our SprintCapableTemplate mock
        when(templateFactory.getTemplate("scrum")).thenReturn(sprintTemplate);

        // STEP 2: Mock createSprint on the SprintCapableTemplate
        when(sprintTemplate.createSprint(1L, sprintDTO)).thenReturn(sprintDTO);

        // STEP 3: Call service method (real)
        SprintDTO result = sprintService.createSprint(1L, sprintDTO, "scrum");

        // STEP 4: Verify result
        assertThat(result).isEqualTo(sprintDTO);
        verify(templateFactory).getTemplate("scrum");
        verify(sprintTemplate).createSprint(1L, sprintDTO);
    }

    @Test
    void getAllSprints_shouldReturnList() {
        when(templateFactory.getTemplate("scrum")).thenReturn(sprintTemplate);
        when(sprintTemplate.getAllSprint(1L)).thenReturn(List.of(sprintDTO));

        List<SprintDTO> result = sprintService.getAllSprints(1L, "scrum");

        assertThat(result).hasSize(1);
        verify(sprintTemplate).getAllSprint(1L);
    }
}
