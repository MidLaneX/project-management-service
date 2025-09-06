package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Six Sigma Template Implementation
 * ----------------------------------
 * Supports DMAIC process improvement.
 */
@Component
public class SixSigmaTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> SIXSIGMA_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("sixsigma", "DMAIC Phases"),
            new FeatureDescriptor("estimation", "Improvement Metrics")
    );

    public SixSigmaTemplateImpl(ProjectRepository projectRepo,
                                TaskRepository taskRepo,
                                TeamProjectRepository teamProjectRepository,
                                UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null, teamProjectRepository, taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "sixsigma";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return SIXSIGMA_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }
}
