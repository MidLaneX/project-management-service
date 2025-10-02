package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Matrix Template Implementation
 * -------------------------------
 * Cross-functional collaboration across departments.
 */
@Component
public class MatrixTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> MATRIX_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("matrix", "Cross-Functional Management"),
            new FeatureDescriptor("board", "Department Board"),
            new FeatureDescriptor("TeamManagement", "Team Management")
    );

    public MatrixTemplateImpl(ProjectRepository projectRepo,
                              TaskRepository taskRepo,

                              UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null,  taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "matrix";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return MATRIX_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }
}
