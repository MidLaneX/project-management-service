package com.midlane.project_management_tool_project_service.template.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import org.springframework.stereotype.Component;

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
            new FeatureDescriptor("list", "list Management"),
            new FeatureDescriptor("board", "Task Board"),
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("calender", "calender")



    );

    public TraditionalTemplateImpl(ProjectRepository projectRepo,
                                   TaskRepository taskRepo,

                                   UserProjectRepository userProjectRepository) {
        super(projectRepo, null, null, taskRepo, userProjectRepository);
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


}
