package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.template.SprintCapableTemplate;
import com.midlane.project_management_tool_project_service.template.Template;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final TemplateFactory templateFactory;

    private SprintCapableTemplate getSprintTemplate(String template) {
        Template t = templateFactory.getTemplate(template);
        if (t instanceof SprintCapableTemplate sprintTemplate) {
            return sprintTemplate;
        }
        throw new UnsupportedOperationException("This template does not support sprints");
    }

    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO, String template) {
        return getSprintTemplate(template).createSprint(projectId, sprintDTO);
    }

    public SprintDTO getSprint(Long projectId, String template) {
        return getSprintTemplate(template).getSprint(projectId);
    }

    public List<SprintDTO> getAllSprints(Long projectId, String template) {
        return getSprintTemplate(template).getAllSprint(projectId);
    }

    public SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO, String template) {
        return getSprintTemplate(template).updateSprint(projectId, sprintId, sprintDTO);
    }

    public void deleteSprint(Long projectId, Long sprintId, String template) {
        getSprintTemplate(template).deleteSprint(projectId, sprintId);
    }
}
