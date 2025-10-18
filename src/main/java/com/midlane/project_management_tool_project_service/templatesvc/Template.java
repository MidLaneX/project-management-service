
package com.midlane.project_management_tool_project_service.templatesvc;

import com.midlane.project_management_tool_project_service.dto.*;

import java.nio.file.AccessDeniedException;
import java.util.List;


public interface Template {

    String getTemplateType();
    List<SprintCapableTemplate.FeatureDescriptor> getAvailableFeatures();
    ProjectDTO createProject(ProjectDTO dto);
    ProjectDTO getProject(Long projectId);
    ProjectDTO updateProject(Long userId, Long projectId, ProjectDTO dto);
    void deleteProject(Long userId, Long projectId);
    List<ProjectDTO> getProjectsForUser(Long userId, Long orgId);
    List<UserProjectDTO> assignTeamToProject(Long userId, Long projectId, Long teamId) throws AccessDeniedException;
    Long getAssignedTeamOfProject(Long projectId);
    TaskDTO createTask(Long projectId, TaskDTO dto);
    TaskDTO updateTask(Long id, TaskDTO dto);
    void deleteTask(Long id);
    TaskDTO getTaskById(Long id);
    List<TaskDTO> getTasksByProject(Long projectId);
    TaskDTO updateTaskStatus(Long id, String status);
    TaskDTO updateSprint(Long projectId, Long sprintId);


}
