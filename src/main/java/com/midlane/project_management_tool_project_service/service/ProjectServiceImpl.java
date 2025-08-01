package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.Feature;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.model.Template;
import com.midlane.project_management_tool_project_service.repository.FeatureRepository;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TemplateRepository;
import com.midlane.project_management_tool_project_service.strategy.StrategyFactory;
import com.midlane.project_management_tool_project_service.strategy.TemplateStrategy;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private TemplateRepository templateRepo;
    @Autowired
    private FeatureRepository featureRepo;
    @Autowired
    private StrategyFactory strategyFactory;

    public Project createProject(String projectName, String templateName) {
        Template template = templateRepo.findByName(templateName).orElseThrow();
        Project project = new Project();
        project.setName(projectName);
        project.setTemplate(template);

        project = projectRepo.save(project);

        TemplateStrategy strategy = strategyFactory.getStrategy(templateName);
        List<String> featureNames = strategy.getFeatures();

        for (String fname : featureNames) {
            Feature f = new Feature();
            f.setName(fname);
            f.setTemplate(template);
            f.setProject(project);
            featureRepo.save(f);
        }

        return project;
    }

}