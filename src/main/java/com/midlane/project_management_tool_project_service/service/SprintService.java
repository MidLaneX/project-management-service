package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final TemplateFactory templateFactory;

    // Create sprint using template-specific implementation
    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO, String template) {
        return templateFactory.getTemplate(template).createSprint(projectId, sprintDTO);
    }

    // Get the latest sprint for a project
    public SprintDTO getSprint(Long projectId, String template) {

        return templateFactory.getTemplate(template).getSprint(projectId);
    }

    // Get all sprints for a project
    public List<SprintDTO> getAllSprints(Long projectId, String template) {
        return templateFactory.getTemplate(template).getAllSprint(projectId);
    }

    // Update sprint info
    public SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO, String template) {
        return templateFactory.getTemplate(template).updateSprint(projectId, sprintId, sprintDTO);
    }

    // Delete a sprint
    public void deleteSprint(Long projectId, Long sprintId, String template) {
        templateFactory.getTemplate(template).deleteSprint(projectId, sprintId);
    }
}
