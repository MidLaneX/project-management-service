package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Startup Template Implementation
 * --------------------------------
 * Startup lifecycle from ideation to scaling.
 */
@Component
public class StartupTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> STARTUP_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("startup", "Startup Stages"),
            new FeatureDescriptor("kanban", "Agile Board"),
            new FeatureDescriptor("backlog", "Feature Planning")
    );

    public StartupTemplateImpl(ProjectRepository projectRepo,
                               TaskRepository taskRepo,
                               TeamProjectRepository teamProjectRepository,
                               UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null, teamProjectRepository, taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "startup";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return STARTUP_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }
}
