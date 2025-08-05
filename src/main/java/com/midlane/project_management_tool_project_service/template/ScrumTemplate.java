// ScrumTemplate.java
package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;

import java.util.List;


public interface ScrumTemplate extends Template {
    ProjectDTO getProject(ProjectDTO dto);

    ProjectDTO createProject(ProjectDTO dto);

    SprintDTO createSprint(Long projectId, SprintDTO sprintDTO);

    SprintDTO getSprint(ProjectDTO dto);

    StoryDTO createStory(Long projectId, StoryDTO storyDTO);

    StoryDTO getStory(ProjectDTO dto);

    UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectRequestDTO userProjectRequestDTO);



    List<UserProjectDTO> getUsersOfProject(ProjectDTO projectDTO);

    List<ProjectDTO> getProjectsOfUser(UserProjectRequestDTO userProjectRequestDTO);

}