package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.Feature;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.model.Template;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TemplateRepository;
import com.midlane.project_management_tool_project_service.strategy.StrategyFactory;
import com.midlane.project_management_tool_project_service.strategy.TemplateStrategy;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final TemplateRepository templateRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              TemplateRepository templateRepository) {
        this.projectRepository = projectRepository;
        this.templateRepository = templateRepository;
    }

    @Override
    public Project createProject(String projectName, String projectType, String templateName) {
        try {
            logger.info("Creating project '{}', type '{}', template '{}'", projectName, projectType, templateName);

            // Find or create Template
            Template template = templateRepository.findByNameIgnoreCase(templateName)
                    .orElseGet(() -> {
                        logger.warn("Template '{}' not found, creating new", templateName);
                        return new Template(templateName);
                    });

            // Get features and tasks from strategy
            TemplateStrategy strategy = StrategyFactory.getStrategy(templateName);
            Map<String, List<String>> featureTasksMap = strategy.getFeaturesAndTasks();

            Set<Feature> features = new LinkedHashSet<>();

            for (Map.Entry<String, List<String>> entry : featureTasksMap.entrySet()) {
                String featureName = entry.getKey();
                List<String> tasksNames = entry.getValue();

                Feature feature = new Feature(featureName, template);

                Set<Task> tasks = new LinkedHashSet<>();
                for (String taskName : tasksNames) {
                    tasks.add(new Task(taskName, feature));
                }
                feature.setTasks(tasks);

                features.add(feature);
            }

            template.setFeatures(features);

            // Save template cascades features and tasks
            template = templateRepository.save(template);

            // Create and save Project linked to Template
            Project project = new Project(projectName, projectType, template);
            Project savedProject = projectRepository.save(project);

            logger.info("Project '{}' created with ID {}", savedProject.getName(), savedProject.getId());
            return savedProject;

        } catch (IllegalArgumentException e) {
            logger.error("Invalid template: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error creating project", e);
            throw new RuntimeException("Failed to create project", e);
        }
    }
}
