package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kanban Template Implementation
 * -------------------------------
 * Continuous workflow with drag-and-drop board.
 */
@Component
public class KanbanTemplateImpl extends AbstractTemplate {

    private static final List<FeatureDescriptor> KANBAN_FEATURES = List.of(
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("kanban", "Kanban Workflow Board"),
            new FeatureDescriptor("board", "Task Board")
    );

    public KanbanTemplateImpl(ProjectRepository projectRepo,
                              TaskRepository taskRepo,
                              TeamProjectRepository teamProjectRepository,
                              UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null, teamProjectRepository, taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "kanban";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return KANBAN_FEATURES;
    }

    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }

}
