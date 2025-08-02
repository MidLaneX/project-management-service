// Template.java
package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.UserProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.dto.StoryDTO;

public interface Template {
    ProjectDTO createProject(ProjectDTO dto);

    ProjectDTO getProject(ProjectDTO dto);

    SprintDTO getSprint(ProjectDTO dto);
    StoryDTO getStory(ProjectDTO dto);



    UserProjectDTO getUserProject(ProjectDTO dto);

    UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectDTO userProjectDTO);
}
