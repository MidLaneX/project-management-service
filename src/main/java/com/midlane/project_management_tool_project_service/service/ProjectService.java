// ProjectService.java
package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.*;
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
    public ProjectDTO getProject(ProjectDTO dto) {
        return  templateFactory.getTemplate(dto.getTemplateType()).getProject(dto);
    }
    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO) {
        return templateFactory.getScrumTemplate().createSprint(projectId, sprintDTO);
    }


    public StoryDTO createStory(Long projectId, StoryDTO storyDTO) {
        return templateFactory.getScrumTemplate().createStory(projectId, storyDTO);
    }

    public SprintDTO getSprint(ProjectDTO dto) {
        return templateFactory.getTemplate(dto.getTemplateType()).getSprint(dto);
    }

    public  StoryDTO getStory(ProjectDTO dto) {
        return templateFactory.getTemplate(dto.getTemplateType()).getStory(dto);
    }

    public UserProjectDTO createUserProject( ProjectDTO projectDTO,UserProjectRequestDTO userProjectDTO) {
        return templateFactory.getTemplate(projectDTO.getTemplateType()).createUserProject(projectDTO,userProjectDTO);
    }
    public List<UserProjectDTO> getUsersOfProject(ProjectDTO projectDTO) {
       return  templateFactory.getTemplate(projectDTO.getTemplateType()).getUsersOfProject(projectDTO);
    }
    public List<ProjectDTO> getProjectsOfUser(ProjectDTO projectDTO, UserProjectRequestDTO userProjectRequestDTO) {
        return  templateFactory.getTemplate(projectDTO.getTemplateType()).getProjectsOfUser(userProjectRequestDTO);
    }
}
