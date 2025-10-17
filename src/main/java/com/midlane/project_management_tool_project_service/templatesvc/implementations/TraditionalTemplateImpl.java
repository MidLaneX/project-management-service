package com.midlane.project_management_tool_project_service.templatesvc.implementations;

import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import com.midlane.project_management_tool_project_service.templatesvc.SprintCapableTemplate;
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

    private static final List<SprintCapableTemplate.FeatureDescriptor> TRADITIONAL_FEATURES = List.of(
            new SprintCapableTemplate.FeatureDescriptor("list", "list Management"),
            new SprintCapableTemplate.FeatureDescriptor("board", "Task Board"),
            new SprintCapableTemplate.FeatureDescriptor("estimation", "Sprint Reports"),
            new SprintCapableTemplate.FeatureDescriptor("calender", "calender")



    );

    public TraditionalTemplateImpl(ProjectRepository projectRepo,
                                   TaskRepository taskRepo,

                                   UserProjectRepository userProjectRepository) {
        super(projectRepo, null,  taskRepo, userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "traditional";
    }

    @Override
    public List<SprintCapableTemplate.FeatureDescriptor> getAvailableFeatures() {
        return TRADITIONAL_FEATURES;
    }

    /**
     * Traditional template does not support user stories like Scrum,
     * so return null or throw UnsupportedOperationException
     */
//    @Override
//    public TaskDTO getStory(ProjectDTO dto) {
//        throw new UnsupportedOperationException("Stories are not supported in Traditional Template");
//    }


}
