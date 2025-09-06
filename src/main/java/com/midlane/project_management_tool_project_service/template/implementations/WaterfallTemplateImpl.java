package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Waterfall Template Implementation
 * ----------------------------------
 * Sequential phase-based project management.
 */
@Component
public class WaterfallTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> WATERFALL_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("waterfall", "Phase Management"),
            new FeatureDescriptor("timeline", "Project Timeline / Gantt"),
            new FeatureDescriptor("list", "Tabular Task View")

    );

    public WaterfallTemplateImpl(ProjectRepository projectRepo,
                                 TaskRepository taskRepo,
                                 TeamProjectRepository teamProjectRepository,
                                 UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null, teamProjectRepository, taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "waterfall";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return WATERFALL_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }
}
