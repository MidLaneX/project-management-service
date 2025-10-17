package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.templatesvc.TemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final TemplateFactory templateFactory;

    public ProjectDTO createProject(ProjectDTO dto, String templateType) {
        return templateFactory.getTemplate(templateType).createProject(dto);
    }

    public List<ProjectDTO> getProjectsForUser(Long userId, Long orgId, String templateType) {
      return templateFactory.getTemplate(templateType)
              .getProjectsForUser(userId, orgId);
    }

    public ProjectDTO getProject(Long projectId, String templateType) {
        return templateFactory.getTemplate(templateType).getProject(projectId);
    }

    public List<UserProjectDTO> assignTeamToProject(Long UserId, Long projectId, String templateType, Long teamId) throws AccessDeniedException {
        return templateFactory.getTemplate(templateType).assignTeamToProject(UserId,projectId,teamId);
    }

    public Long getAssignedTeamOfProject(Long projectId,String templateType) {
        return templateFactory.getTemplate(templateType).getAssignedTeamOfProject(projectId);
    }


    public ProjectDTO updateProject(Long userId,Long projectId, String templateType, ProjectDTO dto) {
        return templateFactory.getTemplate(templateType).updateProject(userId,projectId, dto);
    }

    public void deleteProject(Long userId,Long projectId, String templateType) {
        templateFactory.getTemplate(templateType).deleteProject(userId,projectId);
    }


}
