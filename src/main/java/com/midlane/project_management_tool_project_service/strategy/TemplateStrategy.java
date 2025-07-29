package com.midlane.project_management_tool_project_service.strategy;

import java.util.List;
import java.util.Map;

public interface TemplateStrategy {
    // Return features and for each feature the list of tasks
    Map<String, List<String>> getFeaturesAndTasks();
}
