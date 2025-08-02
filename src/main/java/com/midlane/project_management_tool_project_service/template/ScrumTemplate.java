// ScrumTemplate.java
package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.UserProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.dto.StoryDTO;


public interface ScrumTemplate extends Template {
    ProjectDTO getProject(ProjectDTO dto);
    ProjectDTO createProject(ProjectDTO dto);
    SprintDTO createSprint(Long projectId, SprintDTO sprintDTO);
    SprintDTO getSprint(ProjectDTO dto);
    StoryDTO createStory(Long projectId, StoryDTO storyDTO);
    StoryDTO getStory(ProjectDTO dto);
    UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectDTO userProjectDTO);
    UserProjectDTO getUserProject(ProjectDTO projectDTO);
}
