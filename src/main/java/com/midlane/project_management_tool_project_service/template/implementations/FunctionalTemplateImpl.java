package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Functional Template Implementation
 * -----------------------------------
 * Traditional hierarchical departmental management.
 */
@Component
public class FunctionalTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> FUNCTIONAL_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("functional", "Department Management"),
            new FeatureDescriptor("list", "Departmental Task List"),
            new FeatureDescriptor("TeamManagement", "Team Management")
    );

    public FunctionalTemplateImpl(ProjectRepository projectRepo,
                                  TaskRepository taskRepo,

                                  UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null,  taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "functional";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return FUNCTIONAL_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }
}
