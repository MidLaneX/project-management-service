package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.template.FeatureDescriptor;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final TemplateFactory templateFactory;

    public ProjectDTO createProject(ProjectDTO dto, String templateType) {
        return templateFactory.getTemplate(templateType).createProject(dto);
    }

    public ProjectDTO getProject(Long projectId, String templateType) {
        return templateFactory.getTemplate(templateType).getProject(projectId);
    }



    public TaskDTO createStory(Long projectId, TaskDTO taskDTO, String templateType) {
        return templateFactory.getTemplate(templateType).createStory(projectId, taskDTO);
    }



//    public TaskDTO getStory(ProjectDTO dto) {
//        return templateFactory.getTemplate(dto.getTemplateType()).getStory(dto);
//    }

    public UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectRequestDTO userProjectDTO) {
        return templateFactory.getTemplate(projectDTO.getTemplateType()).createUserProject(projectDTO, userProjectDTO);
    }

    public List<UserProjectDTO> getUsersOfProject(ProjectDTO projectDTO) {
        return templateFactory.getTemplate(projectDTO.getTemplateType()).getUsersOfProject(projectDTO);
    }

    public List<ProjectDTO> getProjectsOfUser(Long userId, String templateType) {
        return templateFactory.getTemplate(templateType).getProjectsOfUser(userId);
    }

    public List<FeatureDescriptor> getTemplateFeatures(String templateType) {
        return templateFactory.getTemplate(templateType).getAvailableFeatures();
    }


}
