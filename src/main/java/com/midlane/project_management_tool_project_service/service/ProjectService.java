// ProjectService.java
package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.UserProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.dto.StoryDTO;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public UserProjectDTO createUserProject( ProjectDTO projectDTO,UserProjectDTO userProjectDTO) {
        return templateFactory.getTemplate(projectDTO.getTemplateType()).createUserProject(projectDTO,userProjectDTO);
    }
    public UserProjectDTO getUserProject(ProjectDTO dto) {
       return  templateFactory.getTemplate(dto.getTemplateType()).getUserProject(dto);
    }
}
