package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import com.midlane.project_management_tool_project_service.repository.TeamProjectRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Traditional Template Implementation
 * ------------------------------------
 * This template is designed for teams following a structured,
 * phase-based (Waterfall/traditional) project management style.
 */
@Component
public class TraditionalTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> TRADITIONAL_FEATURES = List.of(
            new FeatureDescriptor("backlog", "Backlog Management"),
            new FeatureDescriptor("scrum_board", "Task Board"),
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("timeline", "Sprint TimeLine")
    );

    public TraditionalTemplateImpl(ProjectRepository projectRepo,
                                   TaskRepository taskRepo,
                                   TeamProjectRepository teamProjectRepository,
                                   UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null, teamProjectRepository, taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "traditional";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return TRADITIONAL_FEATURES;
    }

    /**
     * Traditional template does not support user stories like Scrum,
     * so return null or throw UnsupportedOperationException
     */
    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        throw new UnsupportedOperationException("Stories are not supported in Traditional Template");
    }

    /**
     * Traditional template does not have Sprints,
     * so all Sprint-related operations are unsupported.
     */
    @Override
    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO) {
        throw new UnsupportedOperationException("Sprints are not supported in Traditional Template");
    }

    @Override
    public SprintDTO getSprint(Long projectId) {
        throw new UnsupportedOperationException("Sprints are not supported in Traditional Template");
    }

    @Override
    public List<SprintDTO> getAllSprint(Long projectId) {
        return Collections.emptyList();
    }

    @Override
    public SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO) {
        throw new UnsupportedOperationException("Sprints are not supported in Traditional Template");
    }

    @Override
    public void deleteSprint(Long projectId, Long sprintId) {
        throw new UnsupportedOperationException("Sprints are not supported in Traditional Template");
    }
}
