package com.midlane.project_management_tool_project_service.strategy;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class ScrumStrategy implements TemplateStrategy {

    @Override
    public Map<String, List<String>> getFeaturesAndTasks() {
        Map<String, List<String>> features = new LinkedHashMap<>();

        features.put("Backlog", List.of("Create User Story", "Prioritize Backlog"));
        features.put("Sprint Planning", List.of("Assign Sprint", "Estimate Story Points"));
        features.put("Board", List.of("Move Task to In Progress", "Complete Task"));
        features.put("Burndown Chart", List.of("View Burndown"));

        return features;
    }
}
