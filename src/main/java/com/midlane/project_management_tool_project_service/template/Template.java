// Template.java
package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;

import java.util.List;

public interface Template {
    ProjectDTO createProject(ProjectDTO dto);

    ProjectDTO getProject(ProjectDTO dto);

    SprintDTO getSprint(ProjectDTO dto);
    StoryDTO getStory(ProjectDTO dto);





    UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectRequestDTO userProjectRequestDTO);

    List<UserProjectDTO> getUsersOfProject(ProjectDTO projectDTO);

    List<ProjectDTO> getProjectsOfUser(UserProjectRequestDTO userProjectRequestDTO);
}
