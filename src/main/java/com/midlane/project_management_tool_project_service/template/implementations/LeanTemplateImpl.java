package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Lean Template Implementation
 * -----------------------------
 * Focused on value stream mapping & eliminating waste.
 */
@Component
public class LeanTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> LEAN_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("lean", "Value Stream Mapping"),
            new FeatureDescriptor("kanban", "Workflow Visualization")
    );

    public LeanTemplateImpl(ProjectRepository projectRepo,
                            TaskRepository taskRepo,

                            UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null,  taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "lean";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return LEAN_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }
}
