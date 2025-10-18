package com.midlane.project_management_tool_project_service.templatesvc.implementations;

import com.midlane.project_management_tool_project_service.repository.*;
import com.midlane.project_management_tool_project_service.templatesvc.SprintCapableTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Startup Template Implementation
 * --------------------------------
 * Startup lifecycle from ideation to scaling.
 */
@Component
public class StartupTemplateImpl extends AbstractTemplate {

    private static final List<SprintCapableTemplate.FeatureDescriptor> STARTUP_FEATURES = List.of(
            new SprintCapableTemplate.FeatureDescriptor("estimation", "Sprint Reports"),
            new SprintCapableTemplate.FeatureDescriptor("startup", "Startup Stages"),
            new SprintCapableTemplate.FeatureDescriptor("kanban", "Agile Board"),
            new SprintCapableTemplate.FeatureDescriptor("backlog", "Feature Planning")
    );

    public StartupTemplateImpl(ProjectRepository projectRepo,
                               TaskRepository taskRepo,

                               UserProjectRepository userProjectRepository) {
        super(projectRepo, null,   taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "startup";
    }

    @Override
    public List<SprintCapableTemplate.FeatureDescriptor> getAvailableFeatures() {
        return STARTUP_FEATURES;
    }

//    @Override
//    public TaskDTO getStory(ProjectDTO dto) {
//        return null;
//    }
}
