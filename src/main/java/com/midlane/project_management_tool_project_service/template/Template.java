// Template.java
package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;

import java.util.List;

import com.midlane.project_management_tool_project_service.dto.*;
import java.util.List;

public interface Template {
    String getTemplateType();

    List<FeatureDescriptor> getAvailableFeatures();
    ProjectDTO createProject(ProjectDTO dto);
    ProjectDTO getProject(Long projectId);
    UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectRequestDTO userProjectRequestDTO);
    List<UserProjectDTO> getUsersOfProject(ProjectDTO projectDTO);
    List<ProjectDTO> getProjectsOfUser(Long userId);

    TaskDTO getStory(ProjectDTO dto);
    TaskDTO createStory(Long projectId, TaskDTO taskDTO);
    TaskDTO createTask(Long projectId, TaskDTO dto);
    TaskDTO updateTask(Long id, TaskDTO dto);
    void deleteTask(Long id);
    TaskDTO getTaskById(Long id);
    List<TaskDTO> getTasksByProject(Long projectId);
    TaskDTO updateTaskStatus(Long id, String status);
    TaskDTO updateSprint(Long projectId, Long sprintId);



    SprintDTO getSprint(Long  projectId);
    SprintDTO createSprint(Long projectId, SprintDTO sprintDTO);
    List<SprintDTO> getAllSprint(Long projectId);
    SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO);
    void deleteSprint(Long projectId, Long sprintId);
}
